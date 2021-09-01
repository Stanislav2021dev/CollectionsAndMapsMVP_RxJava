package com.example.colmapsrxjavatask4;

import android.os.RemoteException;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;

import com.example.colmapsrxjavatask4.presenters.mapspresenter.MapsPresenter;
import com.example.colmapsrxjavatask4.view.MainActivity;
import com.example.colmapsrxjavatask4.view.mapsView.MapsFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTestMaps {

    private final String valueForTest = "100000";
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    private MapsPresenter mapsPresenter;
    private int numElementsMap;
    private List<TextView> tvList = new ArrayList<>();
    private List<ProgressBar> pbList = new ArrayList<>();
    private FragmentScenario<MapsFragment> scenario;

    @Before
    public void setMapsFragment() {
        scenario = FragmentScenario.launchInContainer(MapsFragment.class);
        scenario.onFragment(fragment -> {
            mapsPresenter = fragment.mapPresenter;
            tvList = fragment.initTvList();
            pbList = fragment.inintPbList();
        });
    }

    public void enterValue(String value) {
        onView(withId(R.id.numMap))
                .perform(click())
                .perform(replaceText(value));

        onView(withId(R.id.testMap))
                .perform(click())
                .perform(closeSoftKeyboard());
    }

    public void rotateDevice() throws RemoteException, InterruptedException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationLeft();
        TimeUnit.SECONDS.sleep(1);
        device.setOrientationNatural();
    }

    @Test
    public void checkCorrectDisplayViewElements() {
        enterValue(valueForTest);
        //Check that button becomes disabled;
        onView(withId(R.id.testMap))
                .check(matches(isNotEnabled()));

        scenario.onFragment(fragment -> {
            numElementsMap = fragment.getNumElementsMap();
        });
        //Check that entered value is correct;
        onView(withId(R.id.numMap))
                .check(matches(withText(String.valueOf(numElementsMap))));

        //Check that timeResults displayed in correctViews;
        String[] timeResult = mapsPresenter.getTimeResArray();
        int index = 0;
        for (TextView tv : tvList) {
            ViewShown.waitViewShown(withId(tv.getId()));
            onView(withId(tv.getId()))
                    .check(matches(withText(timeResult[index])));
            index++;
        }
        //Check that button becomes enable
        onView(withId(R.id.testMap)).check(matches(isEnabled()));

        //Check that all progressBars are turned off;
        for (ProgressBar pb : pbList) {
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

    private void testValue(String val) {
        enterValue(val);
        onView(withId(R.id.numMap))
                .check(matches(withHint(R.string.warning)));
    }

    @Test
    public void checkWithRotation() throws RemoteException, InterruptedException {
        enterValue(valueForTest);
        rotateDevice();
        ViewShown.waitViewShown(withId(R.id.time8));
    }
}