package net.in.ahr.dbms.business.usecases.result.check;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;

import org.apache.commons.lang3.math.NumberUtils;

import greendao.MusicMst;

/**
 * Created by str2653z on 16/05/12.
 */
public class ResultNumberTextWatcher implements TextWatcher {

    TextInputLayout textInputLayout;
    MusicMst musicMst;
    int mode;

    public static final int CHECK_MODE_EX_SCORE = 1;
    public static final int CHECK_MODE_BP = 2;
//    int maxNotes;

    public ResultNumberTextWatcher(TextInputLayout textInputLayout, MusicMst musicMst, int mode) {
        this.textInputLayout = textInputLayout;
        this.musicMst = musicMst;
        this.mode = mode;
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
        String inputStr = s.toString();
        if (inputStr.length() > 0) {
            if (NumberUtils.isNumber(inputStr)) {
                if ( "0".equals(inputStr.substring(0, 1)) && inputStr.length() > 1 ) {
                    textInputLayout.setError("2桁以上で0で始まっています。");
                    textInputLayout.setErrorEnabled(true);
                } else {
                    // EXスコア専用チェック
                    if (mode == CHECK_MODE_EX_SCORE) {
                        checkExScore(inputStr);
                    } else if (mode == CHECK_MODE_BP) {
                        checkBp(inputStr);
                    }


                }
            } else {
                textInputLayout.setError("半角数字を入力してください。");
                textInputLayout.setErrorEnabled(true);
            }
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void checkExScore(String inputStr) {
        int maxScore = MusicResultUtil.retMaxScore(musicMst);
        if ( Long.parseLong(inputStr) > maxScore ) {
            textInputLayout.setError("MAXスコアより大きい値です。");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void checkBp(String inputStr) {
        int maxNotes = MusicResultUtil.retMaxNotes(musicMst);
        if ( Long.parseLong(inputStr) > maxNotes ) {
            textInputLayout.setError("MAXノーツ数より大きい値です。");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        }
    }

}
