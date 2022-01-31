package ru.magenta.lorempicsumtestapp.domain

import ru.magenta.lorempicsumtestapp.core.Abstract
import ru.magenta.lorempicsumtestapp.ui.PictureDomainToUiMapper
import ru.magenta.lorempicsumtestapp.ui.PictureUi

data class PictureDomain(
    private val id: Int,
    private val download: String
) : Abstract.Object<PictureUi, PictureDomainToUiMapper> {
    override fun map(mapper: PictureDomainToUiMapper) = mapper.map(id, download)
}