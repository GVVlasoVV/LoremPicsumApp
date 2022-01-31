package ru.magenta.lorempicsumtestapp.domain

import retrofit2.HttpException
import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.data.PictureData
import ru.magenta.lorempicsumtestapp.ui.PicturesDomainToUiMapper
import ru.magenta.lorempicsumtestapp.ui.PicturesUi
import java.net.UnknownHostException

sealed class PicturesDomain : Abstract.Object<PicturesUi, PicturesDomainToUiMapper> {
    data class Success(
        private val pictures: List<PictureData>,
        private val picMapper: PictureDataToDomainMapper
    ) : PicturesDomain() {
        override fun map(mapper: PicturesDomainToUiMapper) = mapper.map(pictures.map {
            it.map(picMapper)
        })
    }

    data class Fail(private val errorType: ErrorType) : PicturesDomain() {
        override fun map(mapper: PicturesDomainToUiMapper) = mapper.map(errorType)
    }
}