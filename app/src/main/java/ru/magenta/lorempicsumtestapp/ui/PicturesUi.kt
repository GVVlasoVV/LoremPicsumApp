package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

sealed class PicturesUi : Abstract.Object<Unit, PictureCommunication> {
    data class Success(
        private val pictures: List<PictureUi>
    ) : PicturesUi() {
        override fun map(mapper: PictureCommunication) = mapper.map(pictures)
    }
}