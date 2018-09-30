package android.support.v4.app;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lany.box.event.NetWorkEvent;
import com.lany.box.utils.DensityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * 这个基类要放在这个包中是为了复写父类的show(FragmentManager manager, String tag)
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected View mContentView;
    protected boolean canceledOnTouchOutside = true;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(NetWorkEvent event) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!mCancelable) {
            mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        }
        mContentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mContentView);
        init();
        return mContentView;
    }

    protected View findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setLayout(getDialogWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    protected int getDialogWidth() {
        return DensityUtils.dp2px(300);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) {
            Log.w(TAG, "已经显示，忽略......");
        } else {
            mDismissed = false;
            mShownByMe = true;
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            //ft.commit();
            ft.commitAllowingStateLoss();
        }
    }

    public void show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void cancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }
}