package com.example.colmapsrxjavatask4;

import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.model.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.CollectionsPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class JUnitTest implements InjectOperationsInterface, InjectCallableTaskInterface, InjectSubjectInterface, GeneratedComponent {
    private final int numElementsForTest = 100000;
    private final CollectionsPresenter collectionsPresenter = new CollectionsPresenter();
    private FillingCollections fillingCollections;

    @ClassRule
    public static final RxTestSchedulerRule testSchedulerRule = new RxTestSchedulerRule();

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
        collectionsPresenter.createMainObservable(numElementsForTest);
        collectionsPresenter.getMainObservable()
                .test()
                .await()
                .assertNoErrors();
        for (String time : collectionsPresenter.getTimeResArray()) {
            assertNotNull(time);
        }

        System.out.println("TimeResult " + Arrays.toString(collectionsPresenter.getTimeResArray()));
    }

    @Test
    public void progressBarTest() throws InterruptedException {

        collectionsPresenter.createMainObservable(numElementsForTest);
        collectionsPresenter.getMainObservable()
                .test()
                .await()
                .assertNoErrors();
        System.out.println(Arrays.toString(collectionsPresenter.getPbStatusResArray()));
        for (Boolean status : collectionsPresenter.getPbStatusResArray()) {
            assertFalse(status);
        }
    }
}



