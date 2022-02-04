package ru.magenta.lorempicsumtestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.magenta.lorempicsumtestapp.domain.PicturesInteractor

class PicturesViewModelFactory(
    private val picturesInteractor: PicturesInteractor,
    private val domainToUiMapper: PicturesDomainToUiMapper,
    private val communication: PictureCommunication,
    private val pictureCache: PictureCache
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PictureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PictureViewModel(
                picturesInteractor,
                domainToUiMapper,
                communication,
                pictureCache
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}