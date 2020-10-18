package com.damishs.ticketingrealtimedb.ui.Login;

import android.app.Activity;
import android.app.Instrumentation;
import androidx.test.rule.ActivityTestRule;
import com.damishs.ticketingrealtimedb.R;
import com.damishs.ticketingrealtimedb.ui.Reader.TokenReader;

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

public class LoginReaderTest1 {

    @Rule
    public ActivityTestRule<Login> mactivityTestRule = new ActivityTestRule<>(Login.class);

    public  Login loginActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TokenReader.class.getName(),null,false);



    @Before
    public void setUp() throws Exception {
        loginActivity = mactivityTestRule.getActivity();
    }


    @Test
    public void  checkValidLogin1(){

        assertNotNull(loginActivity.findViewById(R.id.btnReaderLogin));

        onView(withId(R.id.btnReaderLogin)).perform(click());
        onView(withId(R.id.editTextReaderID)).perform(replaceText("-MJfI9xuhv24oRqm5-Y9"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity readerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(readerActivity);
        readerActivity.finish();

    };

    @Test
    public void  checkValidLogin2(){

        assertNotNull(loginActivity.findViewById(R.id.btnReaderLogin));

        onView(withId(R.id.btnReaderLogin)).perform(click());
        onView(withId(R.id.editTextReaderID)).perform(replaceText("-MJjJaKCXglhvXc3bg_W"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity readerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(readerActivity);
        readerActivity.finish();

    };



    @Test
    public void  checkWithWhitespaces(){

        assertNotNull(loginActivity.findViewById(R.id.btnReaderLogin));

        onView(withId(R.id.btnReaderLogin)).perform(click());
        onView(withId(R.id.editTextReaderID)).perform(replaceText("-MJfI9xuhv24oRqm5-Y9         "));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity readerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(readerActivity);
        readerActivity.finish();

    };

    @Test
    public void  checkWithInvalidID(){

        assertNotNull(loginActivity.findViewById(R.id.btnReaderLogin));

        onView(withId(R.id.btnReaderLogin)).perform(click());
        onView(withId(R.id.editTextReaderID)).perform(replaceText("aaaaaaaaaaaaaaaaaa"));
        onView(withId(R.id.btnLogin)).perform(click());

        Activity readerActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(readerActivity);
        readerActivity.finish();

    };


    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}