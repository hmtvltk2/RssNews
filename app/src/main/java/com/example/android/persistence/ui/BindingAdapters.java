
package com.example.android.persistence.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.persistence.R;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("bookmark")
    public static void bookmark(ImageView view, int bookmark) {
        if (bookmark == 1) {
            view.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            view.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, String imageUri) {
        Glide.with(view.getContext()).load(imageUri).crossFade().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(view);
    }

    @BindingAdapter("android:background")
    public static void setBackground(View view, String imageUri){
        Glide.with(view.getContext()).load(imageUri).crossFade().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground(resource);
            }
        });
    }


    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("postTime")
    public static void setPostTime(TextView textView, String text){

    }
}