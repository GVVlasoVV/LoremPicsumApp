package ru.magenta.lorempicsumtestapp.data.cloud

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.data.PictureData

class PictureCloudMapper : Abstract.Mapper {
    fun map(pictureInfoCloud: PictureInfoCloud) =
        PictureData(pictureInfoCloud.id, pictureInfoCloud.download)
}