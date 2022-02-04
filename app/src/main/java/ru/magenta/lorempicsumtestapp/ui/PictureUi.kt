package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.ui.PictureUi.UrlMapper

sealed class PictureUi :
    Abstract.Object<Unit, UrlMapper>,
    ComparablePictureUi,
    Favorite,
    Matcher {

    override fun matches(arg: String) = false
    override fun map(mapper: UrlMapper) = Unit
    override fun onLike(like: Boolean) = Unit
    data class Success(
        val id: String,
        val download: String,
        var like: Boolean
    ) : PictureUi() {
        override fun matches(arg: String) = arg == id
        override fun sameContent(pictureUi: PictureUi) =
            pictureUi is Success && download == pictureUi.download

        override fun same(pictureUi: PictureUi) = pictureUi is Success && id == pictureUi.id

        override fun map(mapper: UrlMapper) = mapper.map(download)

        override fun clickLike(mapper: FavoriteMapper) = mapper.like(id, like)
        override fun onLike(like: Boolean) {
            this.like = like
        }

        override fun likeOrUnlike(listener: FavoriteListener) =
            listener.likeOrUnlike(Integer.parseInt(id))
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

interface Matcher {
    fun matches(arg: String): Boolean
}

interface ComparablePictureUi {
    fun sameContent(pictureUi: PictureUi) = false
    fun same(pictureUi: PictureUi) = false
}