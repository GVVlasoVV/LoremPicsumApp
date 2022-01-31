package ru.magenta.lorempicsumtestapp.domain

import retrofit2.HttpException
import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.data.PictureData
import java.net.UnknownHostException

class PicturesDataToDomainMapper(
    private val mapper: PictureDataToDomainMapper
) : Abstract.Mapper {
    fun map(pictures: List<PictureData>) = PicturesDomain.Success(pictures, mapper)

    fun map(e: Exception) = PicturesDomain.Fail(
        when (e) {
            is UnknownHostException -> ErrorType.NO_CONNECTION
            is HttpException -> ErrorType.SERVICE_UNAVAILABLE
            else -> ErrorType.GENERIC_ERROR
        })
}