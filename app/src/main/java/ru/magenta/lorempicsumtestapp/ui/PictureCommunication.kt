package ru.magenta.lorempicsumtestapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.magenta.lorempicsumtestapp.core.Abstract

class PictureCommunication : Abstract.Mapper {
    private val listLiveData = MutableLiveData<List<PictureUi>>()
    fun map(pictures: List<PictureUi>) {
        listLiveData.value = pictures
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<PictureUi>>) {
        listLiveData.observe(owner, observer)
    }

}