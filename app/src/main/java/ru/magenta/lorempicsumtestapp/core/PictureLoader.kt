package ru.magenta.lorempicsumtestapp.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.magenta.lorempicsumtestapp.R

class PictureLoader(
    private val context: Context
) {

    private fun memoryCacheKey(id: String) = MemoryCache.Key("${CACHE_KEY}_$id ")

    private val imageLoader = ImageLoader.Builder(context).crossfade(true).build()

    private fun requestImage(url: String) =
        ImageRequest.Builder(context)
            .data(url)
            .placeholder(R.drawable.ic_launcher_background)
            .size(WIDTH, HEIGHT)
            .memoryCachePolicy(CachePolicy.DISABLED)

    private suspend fun result(url: String) = withContext(Dispatchers.IO) {
        (imageLoader.execute(requestImage(url).build()) as SuccessResult).drawable
    }

    fun fetchPicture(url: String, image: ImageView) =
        imageLoader.enqueue(requestImage(url).target(image).build())

    suspend fun fetchBitmap(url: String) = (result(url) as BitmapDrawable).bitmap!!

    suspend fun fetchFavorite(id: String): Bitmap? = withContext(Dispatchers.IO) {
        imageLoader.memoryCache[memoryCacheKey(id)]
    }

    fun setFavorite(id: String, bitmap: Bitmap) {
        imageLoader.memoryCache[memoryCacheKey(id)] = bitmap
    }

    fun removeFavorite(id: String) = imageLoader.memoryCache.remove(memoryCacheKey(id))

    private companion object {
        const val CACHE_KEY = "pictureCacheKey"

        const val WIDTH = 1200
        const val HEIGHT = 800
    }
}