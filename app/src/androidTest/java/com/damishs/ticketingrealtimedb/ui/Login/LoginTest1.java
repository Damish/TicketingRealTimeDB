package com.damishs.ticketingrealtimedb.ui.Login;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.damishs.ticketingrealtimedb.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class LoginTest1 {


    @Rule
    public ActivityTestRule<Login> mactivityTestRule = new ActivityTestRule<Login>(Login.class);

    public  Login mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(HomeActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorAdmin = getInstrumentation().addMonitor(Admin.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        mActivity = mactivityTestRule.getActivity();

    }


    //if user given correct credentials 1
    @Test
    public void  checkValidLogin1(){

        assertNotNull(mActivity.findViewById(R.id.btnLogin));

        onView(withId(R.id.editTextName)).perform(replaceText("damish@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity homeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(homeActivity);

        homeActivity.finish();

    };

    //if Admin credentials are given
    @Test
    public void  checkValidLogin2(){

        assertNotNull(mActivity.findViewById(R.id.btnLogin));

        onView(withId(R.id.editTextName)).perform(replaceText("Admin@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity adminActivity = getInstrumentation().waitForMonitorWithTimeout(monitorAdmin,5000);

        assertNotNull(adminActivity);

        adminActivity.finish();

    };


    //if user given Capital letters for Email but password is correct
    @Test
    public void  checkUserEmailCase(){

        assertNotNull(mActivity.findViewById(R.id.btnLogin));

        onView(withId(R.id.editTextName)).perform(replaceText("DAMISH@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity homeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(homeActivity);

        homeActivity.finish();

    };

    //if user given wrong credentials
    @Test
    public void  checkInvalidCredentials(){

        assertNotNull(mActivity.findViewById(R.id.btnLogin));

        onView(withId(R.id.editTextName)).perform(replaceText("damish@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("000000"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity homeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(homeActivity);

        homeActivity.finish();

    };



    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}