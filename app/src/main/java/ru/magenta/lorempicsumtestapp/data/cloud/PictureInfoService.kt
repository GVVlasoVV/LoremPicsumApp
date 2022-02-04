package ru.magenta.lorempicsumtestapp.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
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
    @Headers("content-type: application/json")
    @GET("id/{id}/info/")
    suspend fun fetchInfoPicture(
        @Path("id") id: Int
    ): ResponseBody
}