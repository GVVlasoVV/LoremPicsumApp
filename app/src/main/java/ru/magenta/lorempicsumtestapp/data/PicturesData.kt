package ru.magenta.lorempicsumtestapp.data

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.domain.PicturesDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PicturesDomain

sealed class PicturesData : Abstract.Object<PicturesDomain, PicturesDataToDomainMapper> {
    data class Success(private val pictures: List<PictureData>) : PicturesData() {
        override fun map(mapper: PicturesDataToDomainMapper) = mapper.map(pictures)
    }
    data class Fail(private val e: Exception) : PicturesData() {
        override fun map(mapper: PicturesDataToDomainMapper) = mapper.map(e)
    }
}