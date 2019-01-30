package com.lany.box.activity;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.gyf.barlibrary.ImmersionBar;
import com.lany.box.R;
import com.lany.box.dialog.LoadingDialog;
import com.lany.box.event.NetWorkEvent;
import com.lany.box.interfaces.OnDoubleClickListener;
import com.lany.box.mvp.view.BaseView;
import com.lany.box.utils.ClickUtil;
import com.lany.box.utils.ViewUtils;
import com.lany.state.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通用基类
 */
public abstract class BaseActivity extends AppCompatActivity implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private View mToolbar;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog;
    protected ImmersionBar mImmersionBar;

    /**
     * 是否需要Toolbar
     */
    protected boolean hasToolbar() {
        return true;
    }

    /**
     * Toolbar是否需要显示返回键
     */
    protected boolean hasBackBtn() {
        return true;
    }

    /**
     * 状态栏的文字和图标是否改成黑色
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 返回Toolbar布局文件id
     */
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_default;
    }

    protected int getToolBarHeight() {
        return getResources().getDimensionPixelSize(R.dimen.actionbar_height);
    }

    /**
     * 返回内容布局文件id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(isStatusBarDarkFont(), 0.2f)
                .navigationBarEnable(false);
        mImmersionBar.init();
        onBeforeSetContentView();
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (hasToolbar()) {
            mToolbar = LayoutInflater.from(this).inflate(getToolBarLayoutId(), null);
            mToolbar.setId(R.id.toolbar);
            mToolbar.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick(View view) {
                    onToolbarDoubleClick();
                }
            }));
            mToolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getToolBarHeight()));
            ViewUtils.setPaddingSmart(mToolbar);
            rootView.addView(mToolbar);
            setBarTitle(getTitle());
            initBackBtn();
        }
        if (getLayoutId() != 0) {
            mStateLayout = new StateLayout(this);
            mStateLayout.setOnRetryListener(this);
            mStateLayout.addView(LayoutInflater.from(this).inflate(getLayoutId(), null));
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            if (hasToolbar()) {
                lp.addRule(RelativeLayout.BELOW, mToolbar.getId());
            }
            rootView.addView(mStateLayout, lp);
        } else {
            throw new IllegalArgumentException("getLayoutId() return 0 , you need a layout file resources");
        }
        setContentView(rootView);
        mUnBinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private void initBackBtn() {
        View backBtn = mToolbar.findViewById(R.id.toolbar_back_btn);
        if (backBtn == null) {
            throw new IllegalArgumentException("Please use the 'R.id.toolbar_back_btn' field to back in custom toolbar layout.");
        }
        if (hasBackBtn()) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ClickUtil.isFast()) {
                        backAction();
                    }
                }
            });
        } else {
            backBtn.setVisibility(View.GONE);
        }
    }


    protected void onBeforeSetContentView() {

    }

    protected void onToolbarDoubleClick() {
        //要实现复写该方法
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideSoftInput();
        }
        return super.onTouchEvent(event);
    }

    protected void hideSoftInput() {
        //点击空白区域收起输入法
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void setBarTitle(@StringRes int resId) {
        setBarTitle(getString(resId));
    }

    public void setBarTitle(CharSequence title) {
        if (hasToolbar()) {
            TextView titleText = mToolbar.findViewById(R.id.toolbar_title_text);
            if (titleText == null) {
                throw new IllegalArgumentException("Please use the 'R.id.toolbar_title_text' field to set title in custom toolbar layout.");
            }
            if (hasToolbar() && !TextUtils.isEmpty(title)) {
                titleText.setText(title);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backAction();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void backAction() {
        onBackPressed();
    }

    @Override
    public void onRetry() {
        log.i("点击重试");
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        log.i("onEvent: 网络发生了变化");
    }

    @Override
    public void showEmpty() {
        mStateLayout.showEmpty();
    }

    @Override
    public void showEmpty(String msg) {
        mStateLayout.showEmpty(msg);
    }

    @Override
    public void showContent() {
        mStateLayout.showContent();
    }

    @Override
    public void showNoWifi() {
        mStateLayout.showNetwork();
    }

    @Override
    public void showError() {
        mStateLayout.showError();
    }

    @Override
    public void showError(String msg) {
        mStateLayout.showError(msg);
    }

    @Override
    public void showLoading() {
        mStateLayout.showLoading();
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    public void showLoadingDialog(CharSequence message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.setMessage(message);
        if (!mLoadingDialog.isAdded()) {
            mLoadingDialog.show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isAdded()) {
            mLoadingDialog.cancel();
        }
    }
}
