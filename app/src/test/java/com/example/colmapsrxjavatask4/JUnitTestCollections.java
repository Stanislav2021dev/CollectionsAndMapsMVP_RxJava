package com.example.colmapsrxjavatask4;


import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.model.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.CollectionsPresenter;

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
public class JUnitTestCollections implements InjectOperationsInterface, InjectCallableTaskInterface, InjectSubjectInterface, GeneratedComponent {
    @ClassRule
    public static final RxTestSchedulerRule testSchedulerRule = new RxTestSchedulerRule();
    private final int numElementsForTest = 100000;
    private final CollectionsPresenter collectionsPresenter = new CollectionsPresenter();
    private FillingCollections fillingCollections;

    @Before
    public void init() {
        InjectOperationsInterface operationsInterface =
                EntryPoints.get(this, InjectOperationsInterface.class);
        fillingCollections = operationsInterface.getFillingCollection();
        fillingCollections.createFillingCollectionsOperationsList(numElementsForTest);
        fillingCollections.createCollectionsList();
    }

    @Test
    public void fillingCollectionsTest() {
        assertEquals(numElementsForTest, fillingCollections.fillArrayList(numElementsForTest)
                .apply(fillingCollections.getArrayList()).size());
        assertEquals(numElementsForTest, fillingCollections.fillLinkedList(numElementsForTest)
                .apply(fillingCollections.getLinkedList()).size());
        assertEquals(numElementsForTest, fillingCollections.fillCopyOnWriteArrayList(numElementsForTest)
                .apply(fillingCollections.getCopyOnWriteArrayList()).size());
    }

    @Test
    public void timeResultsTest() throws InterruptedException {
        collectionsPresenter.start(numElementsForTest);
        collectionsPresenter.getSubjectTime()
                .test()
                .await()
                .assertNoErrors();
        for (String time : collectionsPresenter.getTimeResArray()) {
            assertNotNull(time);
        }
    }

    @Test
    public void progressBarTest() throws InterruptedException {
        collectionsPresenter.start(numElementsForTest);
        collectionsPresenter.getSubjectPbStatus()
                .test()
                .await()
                .assertNoErrors();
        for (Boolean status : collectionsPresenter.getPbStatusResArray()) {
            assertFalse(status);
        }
    }
}

