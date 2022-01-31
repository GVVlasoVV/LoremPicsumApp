package ru.magenta.lorempicsumtestapp.data.cloud

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


/**
 * width & height
 * https://picsum.photos/200/300
 */
interface PictureRandomService {
    @GET("100/100/")
    fun fetchRandomPicture(): Call<ResponseBody>
}