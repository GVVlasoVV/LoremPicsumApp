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
    private val list = arrayListOf<PictureData>()
    suspend fun fetchPictures() = try {
        val id = PictureId(service).fetchId()
        val cloud = dataSource.fetchPicture(id)
        val picture = pictureCloudMapper.map(cloud)
        Log.e("picture", id.toString())


        list.add(picture)
        PicturesData.Success(list)

    } catch (e: Exception) {
        Log.e("repository", e.toString())
        PicturesData.Fail(e)
    }
}