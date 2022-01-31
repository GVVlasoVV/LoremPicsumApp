package ru.magenta.lorempicsumtestapp.data.cloud

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * https://picsum.photos/id/0/info
 * "id": "0",
 * "author": "Alejandro Escamilla",
 * "width": 5616,
 * "height": 3744,
 * "url": "https://unsplash.com/...",
 * "download_url": "https://picsum.photos/..."
 */

interface PictureInfoService {
    @GET("id/{id}/info/")
    suspend fun fetchInfoPicture(
        @Path("id") id: Int
    ): PictureInfoCloud
}