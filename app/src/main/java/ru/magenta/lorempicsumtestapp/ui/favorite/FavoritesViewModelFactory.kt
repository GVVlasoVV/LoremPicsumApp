package ru.magenta.lorempicsumtestapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.magenta.lorempicsumtestapp.data.Repository
import ru.magenta.lorempicsumtestapp.ui.PictureCache

class FavoritesViewModelFactory(
    private val repository: Repository,
    private val communication: FavoriteCommunication,
    private val pictureCache: PictureCache
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(
                repository,
                communication,
                pictureCache
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}