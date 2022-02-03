package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureDomainToUiMapper : Abstract.Mapper {
    fun map(id: String, download: String) = PictureUi.Success(id, download)
}
