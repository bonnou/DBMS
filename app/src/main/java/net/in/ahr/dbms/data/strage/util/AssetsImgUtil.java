package net.in.ahr.dbms.data.strage.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by str2653z on 2016/03/22.
 */
public class AssetsImgUtil {
    public Bitmap getAssetsImg(Context context, String assetsImgPath) {
        LogUtil.logEntering();

        Bitmap result = null;

        try {
            InputStream istream = context.getResources().getAssets().open(assetsImgPath);
            result = BitmapFactory.decodeStream(istream);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 適切な例外処理
        }

        LogUtil.logExiting();
        return result;
    }
}
