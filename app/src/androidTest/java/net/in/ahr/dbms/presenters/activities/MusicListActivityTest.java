package net.in.ahr.dbms.presenters.activities;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.testCommon.DbmsUnitTestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by str2653z on 16/05/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicListActivityTest {
    private static final String TAG = MusicListActivityTest.class.getSimpleName();
    private Activity mActivity;

    static {
        // システムプロパティ一覧出力
        Properties props = System.getProperties();
        props.list(System.out);

        DbmsUnitTestUtils.replaceMusicMstCsvForUt();
    }

    @Rule
    public ActivityTestRule<MusicListActivity> mActivityRule = new ActivityTestRule(MusicListActivity.class);

    @Before
    public void setUp(){
        LogUtil.logDebug("setUp");

        DbmsUnitTestUtils.replaceMusicMstCsvForUt();

        // Activityの取得
        mActivity = mActivityRule.getActivity();

    }
    // @Testが使えるので日本語だけのテストメソッドが書けるようになった
    @Test
    public void espressoTestSample() {

        LogUtil.logDebug("espressoTestSample");

//        onView(withId(R.id.musicListView)).

//        Assert.assertNotNull(mActivity);
//        onView(withId(R.id.fragment)).check(matches(isDisplayed()));
//        onView(withId(R.id.musicListView)).check(matches(isDisplayed()));

    }


}