package com.lany.box.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lany.box.helper.ImageHelper;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import butterknife.ButterKnife;

public class ItemViewHolder extends BaseViewHolder {

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        return super.setText(viewId, TextUtils.isEmpty(value) ? "" : value);
    }

    public BaseViewHolder setText(@IdRes int viewId, int value) {
        return setText(viewId, String.valueOf(value));
    }

    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    public ItemViewHolder setImageUrl(@IdRes int viewId, String picUrl) {
        ImageView imageView = getView(viewId);
        ImageHelper.getInstance().show(imageView, picUrl);
        return this;
    }

    public ItemViewHolder setAvatarUrl(@IdRes int viewId, String picUrl) {
        ImageView imageView = getView(viewId);
        imageView.getLayoutParams().height = imageView.getLayoutParams().width;
        ImageHelper.getInstance().showAvatar(imageView, picUrl);
        return this;
    }

    public ItemViewHolder showCircle(@IdRes int viewId, String picUrl) {
        ImageView imageView = getView(viewId);
        ImageHelper.getInstance().showCircle(imageView, picUrl);
        return this;
    }

    public ItemViewHolder setTextSize(@IdRes int viewId, float size) {
        TextView view = getView(viewId);
        view.setTextSize(size);
        return this;
    }

    public ItemViewHolder setVisibility(@IdRes int viewId, int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            getView(viewId).setVisibility(visibility);
        } else {
            throw new RuntimeException("Parameter visibility is an illegal value！");
        }
        return this;
    }
}
