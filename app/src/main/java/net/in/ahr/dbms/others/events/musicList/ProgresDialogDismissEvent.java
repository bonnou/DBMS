package net.in.ahr.dbms.others.events.musicList;

import net.in.ahr.dbms.data.strage.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by str2653z on 2016/04/16.
 */
public class ProgresDialogDismissEvent {

    public void start() {
        LogUtil.logEntering();

        EventBus.getDefault().post(this);

        LogUtil.logExiting();
    }

}
