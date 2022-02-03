package ru.magenta.lorempicsumtestapp.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.magenta.lorempicsumtestapp.data.cloud.PictureInfoService
import ru.magenta.lorempicsumtestapp.data.cloud.PictureRandomService
import java.util.concurrent.TimeUnit

class Network {

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .callTimeout(1, TimeUnit.MINUTES)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun pictureRandomService(): PictureRandomService = retrofit.create(PictureRandomService::class.java)
    fun pictureInfoService(): PictureInfoService = retrofit.create(PictureInfoService::class.java)

    private companion object {
        private const val BASE_URL = "https://picsum.photos"
    }
}