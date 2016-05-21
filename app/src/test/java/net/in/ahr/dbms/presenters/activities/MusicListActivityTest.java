package net.in.ahr.dbms.presenters.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.testCommon.DbmsUnitTestUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
// androidmanifest.xml not found対応
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "net.in.ahr.dbms")
public class MusicListActivityTest {

    static {
        // 読み込むcsvファイルをUT用にする
        DbmsUnitTestUtils.replaceMusicMstCsvForUt();
    }

    private MusicListActivity musicListActivity;

    @Before
    public void setup() throws Exception {

        // Robolectricで標準出力にログを出力する
        // http://horie1024.hatenablog.com/entry/2014/03/03/002147
//        ShadowLog.stream = System.out;

        // ファイル出力
        ShadowLog.stream = new PrintStream(new FileOutputStream("/Users/str2653z/AndroidStudioProjects/logs/DBMS/app/junit.log"), true);

        // 読み込むcsvファイルをUT用にする
//        DbmsUnitTestUtils.replaceMusicMstCsvForUt();

        // Activity準備
//        musicListActivity = Robolectric.setupActivity(MusicListActivity.class);
        ActivityController<MusicListActivity> controller = Robolectric.buildActivity(MusicListActivity.class);
        controller.setup();

        LogUtil.logDebug("controller.setup() finished");

//        musicListActivity = Robolectric.buildActivity(MusicListActivity.class).setup().get();
//        assertNotNull("musicListActivity not intsantiated", musicListActivity);

//        lstView = (ListView) musicListActivity.findViewById(R.id.musicListView);
//        assertNotNull("ListView not found ", lstView);

//        ShadowLog.stream = System.out;
    }

    @Test
    public void clickListViewItem() {

        LogUtil.logDebug("clickListViewItem start");

//        Bundle savedInstanceState = new Bundle();
//        musicListActivity.onCreate(savedInstanceState);

//        ShadowListView shadowListView = Shadows.shadowOf(lstView);
//        shadowListView.populateItems();/

    }

}