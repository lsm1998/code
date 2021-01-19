package com.lsm1998.log4j.layout;

import com.lsm1998.log4j.LoggingEvent;

/**
 * 纯文本布局，直接调用{@link LoggingEvent#toString()}
 */
public class PlainLayout implements Layout {

    @Override
    public String doLayout(LoggingEvent e) {
        return e.toString();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
