package com.github.lany192.box.utils;

import com.elvishew.xlog.printer.file.naming.FileNameGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LogFileNameGenerator implements FileNameGenerator {

    ThreadLocal<SimpleDateFormat> mLocalDateFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
        }
    };

    @Override
    public boolean isFileNameChangeable() {
        return true;
    }

    /**
     * Generate a file name which represent a specific date.
     */
    @Override
    public String generateFileName(int logLevel, long timestamp) {
        SimpleDateFormat sdf = mLocalDateFormat.get();
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(timestamp)) + ".log";
    }
}
