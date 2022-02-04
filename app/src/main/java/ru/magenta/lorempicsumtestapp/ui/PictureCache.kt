package ru.magenta.lorempicsumtestapp.ui

import android.content.Context
import android.util.Log

class PictureCache(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(ID_LIST_NAME, Context.MODE_PRIVATE)

    private val idSet = mutableSetOf<Int>()

    private fun initId(): Set<Int> {
        val set = sharedPreferences.getStringSet(IDS_KEY, emptySet()) ?: emptySet()
        Log.e("init", set.toString())
        return set.mapTo(HashSet()) { it.toInt() }
    }

    fun readId() {
        idSet.addAll(initId())
    }

    fun getSetId(): Set<Int> {
        return initId()
    }

    private fun saveId(id: Int) {
        idSet.add(id)
    }

    private fun clearId(id: Int) {
        idSet.remove(id)
    }

    fun finishId() {
        val set = idSet.mapTo(HashSet()) { it.toString() }
        Log.e("finish", set.toString())
        sharedPreferences.edit().putStringSet(IDS_KEY, set).apply()
    }

    fun matchId(id: Int) = id in idSet

    fun changeState(id: Int) {
        if (matchId(id)) {
            Log.e("click delete PC", id.toString())
            clearId(id)
        } else {
            Log.e("click add PC", id.toString())
            saveId(id)
        }
    }

    private companion object {
        const val ID_LIST_NAME = "picturesList"
        const val IDS_KEY = "picturesKey"
    }
}