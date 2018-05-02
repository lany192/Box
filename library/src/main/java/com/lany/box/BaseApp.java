package com.lany.box;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.lany.box.utils.LogFileFormat;
import com.lany.box.widget.RefreshView;
import com.lany.sp.BuildConfig;
import com.lany.sp.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public abstract class BaseApp extends Application {
    protected final String TAG = getClass().getSimpleName();
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SPHelper.getInstance().init(this, BuildConfig.DEBUG);
        context = getApplicationContext();
        initLog();
        initCatchException();
        initRefreshView();
    }

    private void initCatchException() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable e) {
                XLog.tag(TAG).e(e.getLocalizedMessage());
                XLog.tag(TAG).st(10).e(TAG, "程序崩溃退出", e);
                Log.e(TAG, "程序崩溃退出", e);
            }
        });
    }

    private void initRefreshView() {
        //设置刷新头颜色
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.refresh_head_background, R.color.refresh_head_text_color);
                return new RefreshView(context).setArrowResource(R.drawable.vector_arrow_gray);
            }
        });
    }

    private void initLog() {
        LogConfiguration config = new LogConfiguration
                .Builder()
                .logLevel(getLogLevel())
                .tag("XLog")
                .build();

        Printer filePrinter = new FilePrinter
                .Builder(getLogFilePath())
                .fileNameGenerator(new DateFileNameGenerator())
                .logFlattener(new LogFileFormat())
                .build();

        XLog.init(config, new AndroidPrinter(), filePrinter);
        XLog.tag(TAG).i("进程名称:" + getProcessName() + "  pid:" + android.os.Process.myPid());
    }

    protected String getLogFilePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/xlog/" + getPackageName() + "/";
    }

    protected int getLogLevel() {
        return LogLevel.ALL;
    }

    protected String getProcessName() {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == android.os.Process.myPid()) {
                    return processInfo.processName;
                }
            }
        }
        return "";
    }

    public static Context getContext() {
        return context;
    }
}
