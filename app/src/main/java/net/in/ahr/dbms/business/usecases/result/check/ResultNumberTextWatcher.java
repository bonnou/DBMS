package net.in.ahr.dbms.business.usecases.result.check;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Spinner;

import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.others.AppConst;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Timer;
import java.util.TimerTask;

import greendao.MusicMst;

/**
 * Created by str2653z on 16/05/12.
 */
public class ResultNumberTextWatcher implements TextWatcher {

    TextInputLayout textInputLayout;
    MusicMst musicMst;
    int mode;
    Spinner clearLampSpinner;
//    FragmentActivity activity;

    public static final int CHECK_MODE_EX_SCORE = 1;
    public static final int CHECK_MODE_BP = 2;
    public static final int CHECK_MODE_CLEAR_PROGRESS = 3;

//    // 0.5秒後にチェックするため
//    // ※http://stackoverflow.com/questions/12142021/how-can-i-do-something-0-5-second-after-text-changed-in-my-edittext
//    private Timer timer=new Timer();
//    private final long DELAY = 500; // milliseconds

/*
以下エラーにより0.5秒ディレイチェックは断念
05-13 10:30:13.188 28047-28139/net.in.ahr.dbms.debug E/AndroidRuntime: FATAL EXCEPTION: Timer-8
                                                                       Process: net.in.ahr.dbms.debug, PID: 28047
                                                                       java.lang.NullPointerException: Attempt to invoke virtual method 'void android.support.v4.app.FragmentActivity.runOnUiThread(java.lang.Runnable)' on a null object reference
                                                                           at net.in.ahr.dbms.business.usecases.result.check.ResultNumberTextWatcher$1.run(ResultNumberTextWatcher.java:83)
                                                                           at java.util.Timer$TimerImpl.run(Timer.java:284)
05-13 10:30:13.188 662-678/system_process W/ActivityManager:   Force finishing activity net.in.ahr.dbms.debug/net.in.ahr.dbms.presenters.activities.MusicListActivity
05-13 10:30:13.206 635-635/? E/EGL_emulation: tid 635: eglCreateSyncKHR(1181): error 0x3004 (EGL_BAD_ATTRIBUTE)
*/

    public ResultNumberTextWatcher(TextInputLayout textInputLayout,
                                   MusicMst musicMst,
                                   int mode,
                                   Spinner clearLampSpinner,
                                   FragmentActivity activity) {
        this.textInputLayout = textInputLayout;
        this.musicMst = musicMst;
        this.mode = mode;
        if (mode == CHECK_MODE_CLEAR_PROGRESS) {
            if (clearLampSpinner == null) {
                throw new IllegalStateException("残ゲージor到達ノーツ数の場合、クリアランプSpinnerオブジェクトが必要です。");
//            } else if (activity == null) {
//                throw new IllegalStateException("残ゲージor到達ノーツ数の場合、Activityオブジェクトが必要です。");
            } else {
                this.clearLampSpinner = clearLampSpinner;
//                this.activity = activity;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        // テキスト変更後に変更されたテキストを取り出す
        final String inputStr = s.toString();

//        // 変更の5秒後にリアルタイムチェック実施
//        timer.cancel();
//        timer = new Timer();
//        timer.schedule(
//                new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {

                                if (inputStr.length() > 0) {
                                    if (NumberUtils.isDigits(inputStr)) {
                                        if ("0".equals(inputStr.substring(0, 1)) && inputStr.length() > 1) {
                                            textInputLayout.setError("2桁以上で0で始まっています");
                                            textInputLayout.setErrorEnabled(true);

                                        } else {
                                            // EXスコア専用チェック
                                            if (mode == CHECK_MODE_EX_SCORE) {
                                                checkExScore(inputStr);

                                            } else if (mode == CHECK_MODE_BP) {
                                                checkMaxNotes(inputStr);

                                            } else if (mode == CHECK_MODE_CLEAR_PROGRESS) {
                                                checkClearProgress(inputStr);

                                            }
                                        }

                                    } else {
                                        textInputLayout.setError("半角数字を入力してください");
                                        textInputLayout.setErrorEnabled(true);

                                    }

                                } else {
                                    textInputLayout.setError(null);
                                    textInputLayout.setErrorEnabled(false);

                                }
//
//                            }
//                        });
//
//                    }
//                },
//                DELAY
//        );

    }

    private void checkExScore(String inputStr) {
        int maxScore = MusicResultUtil.retMaxScore(musicMst);
        if ( Integer.parseInt(inputStr) > maxScore ) {
            textInputLayout.setError("MAXスコアより大きい値です");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void checkMaxNotes(String inputStr) {
        int maxNotes = MusicResultUtil.retMaxNotes(musicMst);
        if ( Integer.parseInt(inputStr) > maxNotes ) {
            textInputLayout.setError("MAXノーツ数より大きい値です");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void checkClearProgress(String inputStr) {

        // クリアランプによりチェック内容を切り替え
        // ※記録していたリザルトではなく、現在sねんたくしているクリアランプで決定する
        String clearLamp = (String) clearLampSpinner.getSelectedItem();
        if (
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp)
             || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp)
        ) {
            checkMaxNotes(inputStr);
        } else {
            checkMaxGuage(inputStr);
        }

    }

    private void checkMaxGuage(String inputStr) {
        int inputInt = Integer.parseInt(inputStr);
        if (inputInt % 2 == 1) {
            textInputLayout.setError("奇数は入力できません");
            textInputLayout.setErrorEnabled(true);
        } else {
            if (inputInt == AppConst.GUAGE_CLEAR_BORDER) {
                textInputLayout.setError("クリアしてます");
                textInputLayout.setErrorEnabled(true);
            } else if (inputInt > AppConst.GUAGE_REVERSE_BORDER) {
                textInputLayout.setError("クリアボーダー以上の値です");
                textInputLayout.setErrorEnabled(true);
            } else {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

}
