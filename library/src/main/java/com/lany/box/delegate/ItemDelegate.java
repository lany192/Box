package com.lany.box.delegate;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.adapter.ItemViewHolder;
import com.lany.box.helper.ItemTypeHelper;

import butterknife.ButterKnife;

/**
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class ItemDelegate<T> implements MultiItemEntity, View.OnClickListener {
    private Context mContext;
    private ItemViewHolder mHolder;
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private final T mData;
    @LayoutRes
    private int layoutId;

    public ItemDelegate(@NonNull final T data) {
        this.mData = data;
    }

    public ItemDelegate(@LayoutRes int layoutId, @NonNull final T data) {
        this.layoutId = layoutId;
        this.mData = data;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     *
     * @return item类型
     */
    @Override
    public int getItemType() {
        return ItemTypeHelper.getInstance().getViewType(this);
    }

    /**
     * 站位的大小,默认是2。如需修改复现该方法
     *
     * @return 大小
     */
    public int getSpanSize() {
        return 2;
    }

    public ItemViewHolder getHolder() {
        return mHolder;
    }

    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    public @LayoutRes int getLayoutId() {
        return layoutId;
    }

    /**
     * 初始化方法
     */
    public abstract void init(T data, int position);

    public void convert(ItemViewHolder helper, Context context) {
        this.mContext = context;
        this.mHolder = helper;
        ButterKnife.bind(this, mHolder.itemView);
        mHolder.itemView.setOnClickListener(v -> onItemClicked(mData, helper.getAdapterPosition()));
        init(mData, helper.getAdapterPosition());
    }

    public void onItemClicked(T data, int position) {
        //item 点击事件
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return (T) mHolder.getView(viewId);
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

    public void setTextSize(@IdRes int viewId, float size) {
        mHolder.setTextSize(viewId, size);
    }

    public void setVisibility(@IdRes int viewId, int visibility) {
        mHolder.setVisibility(viewId, visibility);
    }

    @Override
    public void onClick(View view) {

    }
}
