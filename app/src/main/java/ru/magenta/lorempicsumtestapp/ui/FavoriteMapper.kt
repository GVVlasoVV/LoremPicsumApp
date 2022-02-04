package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

interface FavoriteMapper : Abstract.Mapper {
    fun like(id: String, like: Boolean)
}