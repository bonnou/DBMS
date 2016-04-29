package net.in.ahr.dbms.presenters.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.in.ahr.dbms.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by str2653z on 2016/04/25.
 */
@RunWith(AndroidJUnit4.class)
public class MusicListActivityTest {

    @Rule
    public ActivityTestRule<MusicListActivity> activityTestRule = new ActivityTestRule<>(MusicListActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnCreate() throws Exception {
        // ActivityのFloatingActionButtonを取得して
        // クリックイベントを発生させてる
        Espresso.onView(withId(R.id.musicListView));
//        Espresso.onView(withId(R.id.musicListView)).check(matches(isDisplayed()));
    }
}