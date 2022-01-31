package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

sealed class PictureUi :
    Abstract.Object<Unit, PictureUi.UrlMapper>,
    ComparablePictureUi {

    override fun map(mapper: UrlMapper) = Unit
    data class Success(
        val id: Int,
        val download: String
    ) : PictureUi() {
        override fun sameContent(pictureUi: PictureUi) =
            pictureUi is Success && download == pictureUi.download

        override fun same(pictureUi: PictureUi) = pictureUi is Success && id == pictureUi.id
        override fun map(mapper: UrlMapper) = mapper.map(download)
    }


    data class Fail(
        private val message: String
    ) : PictureUi() {
        override fun map(mapper: UrlMapper) = mapper.map(message)
        override fun sameContent(pictureUi: PictureUi) =
            pictureUi is Fail && message == pictureUi.message

        override fun same(pictureUi: PictureUi) = sameContent(pictureUi)
    }

    object Progress : PictureUi()

    interface UrlMapper : Abstract.Mapper {
        fun map(url: String)
    }
}

interface ComparablePictureUi {
    fun sameContent(pictureUi: PictureUi) = false
    fun same(pictureUi: PictureUi) = false
}