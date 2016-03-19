package net.in.ahr.dbms.data.strage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import au.com.bytecode.opencsv.CSVReader;

public class CSVParser {

    public void parse(Context context, String assetsCsvPath) {

        // AssetManagerの呼び出し
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream is = assetManager.open(assetsCsvPath);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < nextLine.length; i++) {
                    Log.d("CSVParser#parse", i + ":" + nextLine[i]);
                }
                Log.d("CSVParser#parse", "■■■■■■■■■■■■■■■■■■■■■■■■");
            }
            is.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}