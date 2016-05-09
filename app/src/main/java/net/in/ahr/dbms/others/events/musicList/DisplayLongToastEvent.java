package net.in.ahr.dbms.others.events.musicList;

import net.in.ahr.dbms.data.strage.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by str2653z on 16/05/04.
 */
public class DisplayLongToastEvent {

    private CharSequence text;
    public CharSequence getText() { return text; }
    public void setText(CharSequence text) { this.text = text; }

    public void start(CharSequence text) {
        LogUtil.logEntering();

        setText(text);

        EventBus.getDefault().post(this);

        LogUtil.logExiting();
    }

}
