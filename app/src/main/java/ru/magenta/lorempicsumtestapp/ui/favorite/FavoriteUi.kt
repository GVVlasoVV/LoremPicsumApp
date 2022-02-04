package ru.magenta.lorempicsumtestapp.ui.favorite

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.ui.Favorite
import ru.magenta.lorempicsumtestapp.ui.FavoriteListener
import ru.magenta.lorempicsumtestapp.ui.FavoriteMapper

sealed class FavoriteUi :
    Abstract.Object<Unit, FavoriteUi.TextMapper>,
    ComparableFavoriteUi,
    Favorite {

    override fun map(mapper: TextMapper) = Unit
    override fun onLike(like: Boolean) = Unit
    data class Success(private val id: Int, var like: Boolean) : FavoriteUi() {
        override fun map(mapper: TextMapper) = mapper.map(id.toString())
        override fun sameContent(favoriteUi: FavoriteUi) =
            favoriteUi is Success && id == favoriteUi.id

        override fun same(favoriteUi: FavoriteUi) = sameContent(favoriteUi)

        override fun clickLike(mapper: FavoriteMapper) = mapper.like(id.toString(), like)
        override fun onLike(like: Boolean) {
            this.like = like
        }

        override fun likeOrUnlike(listener: FavoriteListener) =
            listener.likeOrUnlike(id)
    }

    data class Fail(private val message: String) : FavoriteUi() {
        override fun map(mapper: TextMapper) = mapper.map(message)
        override fun sameContent(favoriteUi: FavoriteUi) =
            favoriteUi is Fail && message == favoriteUi.message

        override fun same(favoriteUi: FavoriteUi) = sameContent(favoriteUi)
    }

    object Progress : FavoriteUi()

    interface TextMapper : Abstract.Mapper {
        fun map(message: String)
    }
}

interface ComparableFavoriteUi {
    fun sameContent(favoriteUi: FavoriteUi) = false
    fun same(favoriteUi: FavoriteUi) = false
}