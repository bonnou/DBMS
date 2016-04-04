package net.in.ahr.dbms.others;

import net.in.ahr.dbms.data.strage.util.LogUtil;

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
            ret = (String) method.invoke(this, null);
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
            // TODO: 適切な例外処理
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
            // TODO: 適切な例外処理
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
            // TODO: 適切な例外処理
        }
        LogUtil.logDebug("ToStringMusicNameMark_ret：" + ret);
        return ret;
    }
}