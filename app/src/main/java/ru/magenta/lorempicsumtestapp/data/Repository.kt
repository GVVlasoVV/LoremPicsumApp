package ru.magenta.lorempicsumtestapp.data

import android.util.Log
import ru.magenta.lorempicsumtestapp.core.Network
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudDataSource
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudMapper
import ru.magenta.lorempicsumtestapp.data.cloud.PictureRandomService

class Repository(
    private val dataSource: PictureCloudDataSource,
    private val pictureCloudMapper: PictureCloudMapper,
    private val service: PictureRandomService
) {
    suspend fun fetchPictures() = try {
        val list = arrayListOf<PictureData>()
        val id = PictureId(service).fetchId()
        val cloud = dataSource.fetchPicture(id)
        val picture = pictureCloudMapper.map(cloud)
        Log.e("picture", "add")
        list.add(picture)
        PicturesData.Success(list)

    } catch (e: Exception) {
        PicturesData.Fail(e)
    }

}