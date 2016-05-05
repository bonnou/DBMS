package net.in.ahr.dbms.others.events.musicList;

import net.in.ahr.dbms.data.strage.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by str2653z on 16/05/04.
 */
public class DoOauthEvent {

    public void start() {
        LogUtil.logEntering();

        EventBus.getDefault().post(this);

        LogUtil.logExiting();
    }

}
