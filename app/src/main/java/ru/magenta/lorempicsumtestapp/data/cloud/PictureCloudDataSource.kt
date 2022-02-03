package ru.magenta.lorempicsumtestapp.data.cloud

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PictureCloudDataSource(private val infoService: PictureInfoService, private val gson: Gson) {

    private val type = object : TypeToken<PictureInfoCloud>() {}.type

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun fetchPicture(id: Int): PictureInfoCloud =
        gson.fromJson(infoService.fetchInfoPicture(id).string(), type)

}