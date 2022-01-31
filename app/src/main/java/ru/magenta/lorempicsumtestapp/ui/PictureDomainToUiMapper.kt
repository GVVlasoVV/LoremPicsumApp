package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureDomainToUiMapper : Abstract.Mapper {
    fun map(id: Int, download: String) = PictureUi.Success(id, download)
}
