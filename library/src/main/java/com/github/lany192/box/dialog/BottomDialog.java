package com.github.lany192.box.dialog;

import android.content.Context;
import android.os.Build;
import androidx.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.ButterKnife;


public abstract class BottomDialog {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected BottomSheetDialog mDialog;
    protected View mContentView;

    protected abstract int getLayoutId();

    public BottomDialog(Context context) {
        mDialog = new BottomSheetDialog(context);
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);
        Window window = mDialog.getWindow();
        if (window != null) {
            //透明背景
            window.findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
            //使状态栏的颜色不变黑
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    protected <T extends View> T getView(@IdRes int viewId) {
        return mContentView.findViewById(viewId);
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void cancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }
}