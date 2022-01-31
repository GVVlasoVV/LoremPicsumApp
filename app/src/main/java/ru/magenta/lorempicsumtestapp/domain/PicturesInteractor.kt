package ru.magenta.lorempicsumtestapp.domain

import ru.magenta.lorempicsumtestapp.data.Repository

class PicturesInteractor(
    private val repository: Repository,
    private val mapper: PicturesDataToDomainMapper
) {
    suspend fun fetchPictures() = repository.fetchPictures().map(mapper)
}
