package com.example.colmapsrxjavatask4;


import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.presenters.mapspresenter.MapsPresenter;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class JUnitTestMaps implements InjectOperationsInterface, InjectCallableTaskInterface, InjectSubjectInterface, GeneratedComponent {
    @ClassRule
    public static final RxTestSchedulerRule testSchedulerRule = new RxTestSchedulerRule();
    private final int numElementsForTest = 100000;
    private final MapsPresenter mapsPresenter = new MapsPresenter();
    private FillingMaps fillingMaps;

    @Before
    public void init() {
        InjectOperationsInterface operationsInterface =
                EntryPoints.get(this, InjectOperationsInterface.class);
        fillingMaps = operationsInterface.getFillingMaps();
        fillingMaps.createFillingMapsOperationsList(numElementsForTest);
        fillingMaps.createMapsList();
    }

    @Test
    public void fillingCollectionsTest() {
        assertEquals(numElementsForTest, fillingMaps.fillHashMap(numElementsForTest)
                .apply(fillingMaps.getHashMap()).size());
        assertEquals(numElementsForTest, fillingMaps.fillTreeMap(numElementsForTest)
                .apply(fillingMaps.getTreeMap()).size());

    }

    @Test
    public void timeResultsTest() throws InterruptedException {
        mapsPresenter.start(numElementsForTest);
        mapsPresenter.getSubjectTime()
                .test()
                .await()
                .assertNoErrors();
        for (String time : mapsPresenter.getTimeResArray()) {
            assertNotNull(time);
        }
    }

    @Test
    public void progressBarTest() throws InterruptedException {
        mapsPresenter.start(numElementsForTest);
        mapsPresenter.getSubjectPbStatus()
                .test()
                .await()
                .assertNoErrors();
        for (Boolean status : mapsPresenter.getPbStatusResArray()) {
            assertFalse(status);
        }
    }
}

