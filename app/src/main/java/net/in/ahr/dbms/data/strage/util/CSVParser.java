package net.in.ahr.dbms.data.strage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;

public class CSVParser {

    public List<String[]> parse(Context context, String assetsCsvPath) {
        LogUtil.logEntering();

        // 返却値の宣言
        List<String[]> resultList = new ArrayList<String[]>();

        // AssetManagerの呼び出し
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream is = assetManager.open(assetsCsvPath);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                resultList.add(nextLine);
            }
            is.close();
            reader.close();
        } catch (IOException e) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90004,
                    AppConst.ERR_STEP_CD_UTIL_00001,
                    AppConst.ERR_MESSAGE_UTIL_00001);
        }

        LogUtil.logExiting();

        return resultList;
    }
}