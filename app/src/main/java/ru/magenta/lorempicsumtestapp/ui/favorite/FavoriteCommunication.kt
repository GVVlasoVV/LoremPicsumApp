package ru.magenta.lorempicsumtestapp.ui.favorite

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.magenta.lorempicsumtestapp.core.Abstract

class FavoriteCommunication : Abstract.Mapper {
    private val listLiveData = MutableLiveData<List<FavoriteUi>>()
    fun map(pictures: List<FavoriteUi>) {
        listLiveData.value = pictures
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<FavoriteUi>>) {
        listLiveData.observe(owner, observer)
    }

    fun isEmpty(): Boolean = listLiveData.value == null
}