package com.example.colmapsrxjavatask4;


import android.os.RemoteException;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.testing.FragmentScenario;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;


import com.example.colmapsrxjavatask4.presenters.collectionspresenter.CollectionsPresenter;
import com.example.colmapsrxjavatask4.view.MainActivity;
import com.example.colmapsrxjavatask4.view.collectionsview.CollectionsFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
    @LargeTest
    public class EspressoTest  {

       private CollectionsPresenter collectionsPresenter;
       private int numElementsCollection;
       private List<TextView> tvList = new ArrayList<>();
       private List<ProgressBar> pbList=new ArrayList<>();
       private final String valueForTest="100000";

    @Rule
        public ActivityScenarioRule<MainActivity> activityRule =
                new ActivityScenarioRule<>(MainActivity.class);


        public void enterValue(String value){
            onView(withId(R.id.numCol))
                    .perform(click())
                    .perform(replaceText(value));

            onView(withId(R.id.testCol))
                    .perform(click())
                    .perform(closeSoftKeyboard());
        }
        public void rotateDevice() throws RemoteException, InterruptedException {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            device.setOrientationLeft();
            TimeUnit.SECONDS.sleep(2);
            device.setOrientationNatural();
        }

        @Test
        public void globalTest() {

            FragmentScenario<CollectionsFragment> scenario = FragmentScenario.launchInContainer(CollectionsFragment.class);
            scenario.onFragment(fragment -> {
                collectionsPresenter= fragment.mCollectionsPresenter;
                tvList=fragment.initTvList();
                pbList=fragment.inintPbList();
            });
            enterValue(valueForTest);

            //Check that button becomes disabled;
            onView(withId(R.id.testCol))
                    .check(matches(isNotEnabled()));

            scenario.onFragment(fragment -> {
                numElementsCollection=fragment.getNumElementsCollection();
            });
            //Check that entered value is correct;
            onView(withId(R.id.numCol))
                    .check(matches(withText(String.valueOf(numElementsCollection))));

            //Check that timeResults displayed in correctViews;
            String[] timeResult= collectionsPresenter.getTimeResArray();
            int index=0;
            for (TextView tv:tvList){
                ViewShown.waitViewShown(withId(tv.getId()));
                onView(withId(tv.getId()))
                        .check(matches(withText(timeResult[index])));
                index++;
            }
            //Check that button becomes enable
            onView(withId(R.id.testCol)).check(matches(isEnabled()));

            //Check that all progressBars are turned off;
            for(ProgressBar pb:pbList){
                onView(withId(pb.getId()))
                        .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            }
        }

        @Test
        public void checkEditTextField() {
            testValue("");
            testValue("0");
            testValue("Some string");
        }
            private void testValue(String val){
            enterValue(val);
            onView(withId(R.id.numCol))
                    .check(matches(withHint(R.string.warning)));
        }

        @Test
        public void checkWithRotation() throws RemoteException, InterruptedException {

            rotateDevice();
            enterValue(valueForTest);
            rotateDevice();
            ViewShown.waitViewShown(withId(R.id.time24));
        }

    }
