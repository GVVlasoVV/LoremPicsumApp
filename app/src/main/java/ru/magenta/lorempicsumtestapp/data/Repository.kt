package ru.magenta.lorempicsumtestapp.data

import android.util.Log
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudDataSource
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudMapper
import ru.magenta.lorempicsumtestapp.data.cloud.PictureRandomService
import ru.magenta.lorempicsumtestapp.ui.PictureCache
import ru.magenta.lorempicsumtestapp.ui.favorite.FavoriteUi
import ru.magenta.lorempicsumtestapp.ui.favorite.FavoritesUi

class Repository(
    private val cloudDataSource: PictureCloudDataSource,
    private val cloudMapper: PictureCloudMapper,
    private val service: PictureRandomService,
    private val pictureCache: PictureCache
) {
    private val list = arrayListOf<PictureData>()
    suspend fun fetchPictures() = try {
        val id = PictureId(service).fetchId()
        val like = pictureCache.matchId(id)
        Log.e("rep", like.toString())
        val cloud = cloudDataSource.fetchPicture(id)
        val picture = cloudMapper.map(cloud, like)
        list.add(picture)

        PicturesData.Success(list)
    } catch (e: Exception) {
        Log.e("repositoryFetchPictures", e.toString())
        PicturesData.Fail(e)
    }

    fun fetchFavorites() = try {
        val favorites = arrayListOf<FavoriteUi>()
        val set = pictureCache.getSetId()
        set.forEach { id ->
            favorites.add(FavoriteUi.Success(id, true))
        }
        FavoritesUi.Success(favorites)
    } catch (e: Exception) {
        Log.e("error", e.toString())
        FavoritesUi.Success(listOf(FavoriteUi.Fail(e.toString())))
    }
}