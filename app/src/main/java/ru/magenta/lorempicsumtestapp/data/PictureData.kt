package ru.magenta.lorempicsumtestapp.data

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.domain.PictureDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PictureDomain

data class PictureData(
    private val id: String,
    private val download: String
) : Abstract.Object<PictureDomain, PictureDataToDomainMapper> {
    override fun map(mapper: PictureDataToDomainMapper): PictureDomain =
        mapper.map(id, download)
}