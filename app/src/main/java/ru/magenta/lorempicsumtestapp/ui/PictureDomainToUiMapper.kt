package ru.magenta.lorempicsumtestapp.ui

import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureDomainToUiMapper() : Abstract.Mapper {
    fun map(id: String, download: String, favorite: Boolean): PictureUi.Success {
        return PictureUi.Success(id, download, favorite)
    }
}
