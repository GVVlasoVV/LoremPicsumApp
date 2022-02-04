package ru.magenta.lorempicsumtestapp.ui.favorite

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.magenta.lorempicsumtestapp.data.Repository
import ru.magenta.lorempicsumtestapp.ui.PictureCache

class FavoriteViewModel(
    private val repository: Repository,
    private val communication: FavoriteCommunication,
    private val pictureCache: PictureCache
) : ViewModel() {

    fun fetchFavorites() {
        if (communication.isEmpty()) {
            communication.map(listOf(FavoriteUi.Progress))
            val favoriteUi = repository.fetchFavorites()
            favoriteUi.map(communication)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<FavoriteUi>>) {
        communication.observe(owner, observer)
    }

    fun likeOrUnlike(id: Int) = pictureCache.changeState(id)

    fun saveState() = pictureCache.finishId()
    fun startCache() = pictureCache.readId()
}