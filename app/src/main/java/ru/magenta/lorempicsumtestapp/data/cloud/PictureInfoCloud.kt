package ru.magenta.lorempicsumtestapp.data.cloud

import com.google.gson.annotations.SerializedName
import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.data.PictureData
import ru.magenta.lorempicsumtestapp.data.PictureDataMapper

/**
 * {
 * "id": "0",
 * "author": "Alejandro Escamilla",
 * "width": 5616,
 * "height": 3744,
 * "url": "https://unsplash.com/...",
 * "download_url": "https://picsum.photos/..."
 * }
 */
class PictureInfoCloud(
    @SerializedName("id")
    val id: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("url")
    val image: String,

    @SerializedName("download_url")
    val download: String

) : Abstract.Object<PictureData, PictureDataMapper> {
    override fun map(mapper: PictureDataMapper) = mapper.map(id, download)
}