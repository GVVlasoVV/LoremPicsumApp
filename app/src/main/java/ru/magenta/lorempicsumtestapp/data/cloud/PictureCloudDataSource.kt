package ru.magenta.lorempicsumtestapp.data.cloud

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PictureCloudDataSource(private val infoService: PictureInfoService, private val gson: Gson) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun fetchPicture(id: Int?): PictureInfoCloud {
        val res = gson.fromJson<PictureInfoCloud>(
            infoService.fetchInfoPicture(id!!).toString(),
            object : TypeToken<PictureInfoCloud>() {}.type
        )
        Log.d("cloudDataSource", "$res  $id")
        return res
    }
}