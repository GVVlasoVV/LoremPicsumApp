package ru.magenta.lorempicsumtestapp.ui.favorite

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.magenta.lorempicsumtestapp.R
import ru.magenta.lorempicsumtestapp.core.PictureLoader
import ru.magenta.lorempicsumtestapp.ui.FavoriteListener
import ru.magenta.lorempicsumtestapp.ui.FavoriteMapper
import ru.magenta.lorempicsumtestapp.ui.Retry

class FavoriteAdapter(
    private val retry: Retry,
    private val pictureLoader: PictureLoader,
    private val favoriteListener: FavoriteListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites = ArrayList<FavoriteUi>()

    fun update(new: List<FavoriteUi>) {
        val diffUtil = DiffUtilCallback(favorites, new)
        val result = DiffUtil.calculateDiff(diffUtil)
        favorites.clear()
        favorites.addAll(new)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int) = when (favorites[position]) {
        is FavoriteUi.Success -> 0
        is FavoriteUi.Fail -> 1
        is FavoriteUi.Progress -> 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        when (viewType) {
            0 -> FavoriteViewHolder.Success(
                R.layout.image_layout.makeView(parent),
                pictureLoader,
                favoriteListener
            )
            1 -> FavoriteViewHolder.Fail(R.layout.fail_layout.makeView(parent), retry)
            else -> FavoriteViewHolder.FullscreenProgress(R.layout.progress_layout.makeView(parent))
        }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(favorites[position])

    override fun getItemCount() = favorites.size

    abstract class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(favoriteUi: FavoriteUi) {}

        class Success(
            view: View,
            private val pictureLoader: PictureLoader,
            private val favoriteListener: FavoriteListener
        ) : FavoriteViewHolder(view) {
            private var bitmap: Bitmap? = null
            private val image = itemView.findViewById<ImageView>(R.id.picture)
            private val favorite = itemView.findViewById<ImageView>(R.id.iv_favorite)


            override fun bind(favoriteUi: FavoriteUi) {
                favoriteUi.map(object : FavoriteUi.TextMapper {
                    override fun map(message: String) {
                        CoroutineScope(Dispatchers.Main).launch {
                            bitmap = pictureLoader.fetchFavorite(message)
                            image.setImageBitmap(bitmap)
                        }
                    }
                })
                favorite.setOnClickListener {
                    favoriteUi.likeOrUnlike(favoriteListener)
                    favoriteUi.clickLike(object : FavoriteMapper {
                        override fun like(id: String, like: Boolean) {
                            favoriteUi.onLike(!like)
                            val iconId = if (like) {
                                pictureLoader.removeFavorite(id)
                                R.drawable.outline_favorite_border_24
                            } else {
                                pictureLoader.setFavorite(id, bitmap!!)
                                R.drawable.outline_favorite_24
                            }
                            favorite.setImageResource(iconId)
                        }
                    })
                }
            }
        }

        class Fail(view: View, private val retry: Retry) : FavoriteViewHolder(view) {
            private val mes = itemView.findViewById<TextView>(R.id.messageTextView)
            private val button = itemView.findViewById<Button>(R.id.tryAgainButton)
            override fun bind(favoriteUi: FavoriteUi) {
                favoriteUi.map(object : FavoriteUi.TextMapper {
                    override fun map(message: String) {
                        mes.text = message
                    }
                })
                button.setOnClickListener {
                    retry.tryAgain()
                }
            }
        }

        class FullscreenProgress(view: View) : FavoriteViewHolder(view)
    }

    class DiffUtilCallback(
        private val oldList: List<FavoriteUi>,
        private val newList: List<FavoriteUi>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].same(newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].sameContent(newList[newItemPosition])
        }
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)