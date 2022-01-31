package ru.magenta.lorempicsumtestapp.domain

import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureDataToDomainMapper : Abstract.Mapper {
    fun map(id: Int, download: String) = PictureDomain(id, download)
}