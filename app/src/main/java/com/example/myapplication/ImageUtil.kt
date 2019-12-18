package com.example.myapplication

import android.widget.ImageView

object ImageUtil {

    fun loadImage(url: String,
                  imageView: ImageView) {
        GlideApp.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}