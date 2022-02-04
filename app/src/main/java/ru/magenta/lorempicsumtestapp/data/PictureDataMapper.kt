package ru.magenta.lorempicsumtestapp.data

import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureDataMapper : Abstract.Mapper {
    fun map(id: String, download: String) = PictureData(id, download, like = false)
}
