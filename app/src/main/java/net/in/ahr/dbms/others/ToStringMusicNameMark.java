package net.in.ahr.dbms.others;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 曲マスタのtoStringを曲名で返却するためのスーパークラス
 *
 * Created by str2653z on 2016/04/04.
 */
public class ToStringMusicNameMark {
    @Override
    public String toString() {
        String ret = null;
        try {
            Method method = this.getClass().getMethod("getName");
            ret = (String) method.invoke(this);
        } catch (Exception e) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90005,
                    AppConst.ERR_STEP_CD_UTIL_00002,
                    AppConst.ERR_MESSAGE_UTIL_00002);
        }
        LogUtil.logDebug("ToStringMusicNameMark_ret：" + ret);
        return ret;
    }
}