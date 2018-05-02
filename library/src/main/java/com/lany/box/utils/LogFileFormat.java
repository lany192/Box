package com.lany.box.utils;


import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.flattener.Flattener;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日志文件输出格式
 */
public class LogFileFormat implements Flattener {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public CharSequence flatten(int logLevel, String tag, String message) {
        return formatter.format(System.currentTimeMillis())
                + '|' + LogLevel.getShortLevelName(logLevel)
                + '|' + tag
                + '|' + message;
    }
}
