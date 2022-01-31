package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.R
import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.domain.ErrorType
import ru.magenta.lorempicsumtestapp.domain.PictureDomain

class PicturesDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val pictureMapper: PictureDomainToUiMapper
) : Abstract.Mapper {
    fun map(books: List<PictureDomain>) = PicturesUi.Success(books.map {
        it.map(pictureMapper)
    })

    fun map(errorType: ErrorType): PicturesUi {
        val messageId = when (errorType) {
            ErrorType.NO_CONNECTION -> R.string.no_connection_error
            ErrorType.SERVICE_UNAVAILABLE -> R.string.server_unavailable_error
            else -> R.string.unknown_error
        }
        val message = resourceProvider.getString(messageId)
        return PicturesUi.Success(listOf(PictureUi.Fail(message)))
    }
}