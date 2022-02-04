package ru.magenta.lorempicsumtestapp.ui

interface Favorite {
    fun clickLike(mapper: FavoriteMapper) = Unit
    fun onLike(like: Boolean)
    fun likeOrUnlike(listener: FavoriteListener) = Unit
}