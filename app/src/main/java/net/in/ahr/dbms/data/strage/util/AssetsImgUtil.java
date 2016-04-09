package net.in.ahr.dbms.data.strage.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by str2653z on 2016/03/22.
 */
public class AssetsImgUtil {
    public Bitmap getAssetsImg(Context context, String assetsImgPath) {
        LogUtil.logEntering();

        Bitmap result = null;

        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(assetsImgPath);
            result = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90006,
                    AppConst.ERR_STEP_CD_UTIL_00003,
                    AppConst.ERR_MESSAGE_UTIL_00003);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    throw new DbmsSystemException(
                            AppConst.ERR_CD_90006,
                            AppConst.ERR_STEP_CD_UTIL_00004,
                            AppConst.ERR_MESSAGE_UTIL_00004);                }
            }
        }

        LogUtil.logExiting();
        return result;
    }
}
