package ru.magenta.lorempicsumtestapp.ui.favorite

import ru.magenta.lorempicsumtestapp.core.Abstract

sealed class FavoritesUi : Abstract.Object<Unit, FavoriteCommunication> {
    data class Success(
        private val favorites: List<FavoriteUi>
    ) : FavoritesUi() {
        override fun map(mapper: FavoriteCommunication) = mapper.map(favorites)
    }
}