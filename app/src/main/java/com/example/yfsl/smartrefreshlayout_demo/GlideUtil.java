package com.example.yfsl.smartrefreshlayout_demo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtil {
    public static void loadChoosePic(Context c, String url, ImageView imageView) {
        Glide.with(c).load(url)
                .skipMemoryCache(true)//内存不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘不缓存
                .placeholder(R.drawable.icon_iv_loading).error(R.drawable.icon_iv_default).centerCrop().into(imageView);
    }
}
