package ru.magenta.lorempicsumtestapp.core

import android.content.Context
import android.util.Log
import android.widget.ImageView
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest

class PictureLoader(
    private val context: Context,
    private val width: Int,
    private val height: Int
) {
    private fun requestImage(url: String, imageView: ImageView) = ImageRequest.Builder(context)
        .data(url)
        .target(imageView)
//        .size(width, height)
        .build()

    fun fetchImage(url: String, imageView: ImageView): Disposable {
        val loader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()
            .enqueue(requestImage(url, imageView))
        Log.e("loader", url)
        return loader
    }


}