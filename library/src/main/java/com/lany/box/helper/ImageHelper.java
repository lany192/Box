package com.lany.box.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lany.box.R;
import com.lany.box.interfaces.BitmapTarget;
import com.lany.box.interfaces.OnImageListener;

import java.io.File;

public class ImageHelper {
    private volatile static ImageHelper instance;

    public static ImageHelper of() {
        if (instance == null) {
            synchronized (ImageHelper.class) {
                if (instance == null) {
                    instance = new ImageHelper();
                }
            }
        }
        return instance;
    }

    private ImageHelper() {

    }

    public void showAvatar(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .override(imageView.getWidth(), imageView.getHeight())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, File file) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(file)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Drawable drawable) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(drawable)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, Bitmap bitmap) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .load(bitmap)
                .apply(options)
                .into(imageView);
    }

    public void show(ImageView imageView, String url, OnImageListener listener) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new BitmapTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (listener != null) {
                            listener.onFinish(imageView, resource.getWidth(), resource.getHeight());
                        }
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {
                        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    }
                });
    }
}
