package com.lany.box.delegate;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.adapter.ItemViewHolder;
import com.lany.box.helper.ImageHelper;
import com.lany.box.helper.ViewTypeHelper;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class MultiDelegate<T> implements MultiItemEntity {
    protected final String TAG = this.getClass().getSimpleName();
    private int mSpanSize = 2;
    private Context mContext;
    private ItemViewHolder mHolder;
    protected Logger.Builder log = XLog.tag(TAG);
    protected final T mData;

    public MultiDelegate(final T data) {
        this.mData = data;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     *
     * @return item类型
     */
    @Override
    public int getItemType() {
        return ViewTypeHelper.getInstance().getViewType(this);
    }

    /**
     * 站位的大小
     *
     * @return 大小
     */
    public int getSpanSize() {
        return mSpanSize;
    }

    public ItemViewHolder getHolder() {
        return mHolder;
    }

    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 初始化方法
     */
    public abstract void init();

    public void convert(ItemViewHolder helper, Context context) {
        this.mContext = context;
        this.mHolder = helper;
        init();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return (T) mHolder.getView(viewId);
    }

    public MultiDelegate setSpanSize(int spanSize) {
        log.i("spanSize:" + spanSize);
        this.mSpanSize = spanSize;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public T getData() {
        return mData;
    }

    public void setText(@IdRes int viewId, CharSequence value) {
        mHolder.setText(viewId, value);
    }

    public void setText(@IdRes int viewId, int value) {
        mHolder.setText(viewId, value);
    }

    public void setTextColor(@IdRes int viewId, @ColorInt int color) {
        mHolder.setTextColor(viewId, color);
    }

    public void setImageUrl(@IdRes int viewId, String picUrl) {
        mHolder.setImageUrl(viewId, picUrl);
    }

    public void setAvatarUrl(@IdRes int viewId, String picUrl) {
        mHolder.setAvatarUrl(viewId, picUrl);
    }

    public void showCircle(@IdRes int viewId, String picUrl) {
        mHolder.showCircle(viewId, picUrl);
    }

    public void setTextSize(@IdRes int viewId, float size) {
        mHolder.setTextSize(viewId, size);
    }

    public void setVisibility(@IdRes int viewId, int visibility) {
        mHolder.setVisibility(viewId, visibility);
    }
}
