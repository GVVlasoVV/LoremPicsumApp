package ru.magenta.lorempicsumtestapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.magenta.lorempicsumtestapp.R
import ru.magenta.lorempicsumtestapp.core.PictureLoader
import java.util.ArrayList

class PictureAdapter(private val retry: Retry, private val pictureLoader: PictureLoader) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    private var pictures = ArrayList<PictureUi>()

    fun update(new: List<PictureUi>) {
        val diffUtil = DiffUtilCallback(pictures, new)
        val result = DiffUtil.calculateDiff(diffUtil)
        pictures.clear()
        pictures.addAll(new)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int) = when (pictures[position]) {
        is PictureUi.Success -> 0
        is PictureUi.Fail -> 1
        is PictureUi.Progress -> 2
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> PictureViewHolder.Success(R.layout.image_layout.makeView(parent), pictureLoader)
        1 -> PictureViewHolder.Fail(R.layout.fail_layout.makeView(parent), retry)
        else -> PictureViewHolder.FullscreenProgress(R.layout.progress_layout.makeView(parent))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) =
        holder.bind(pictures[position])

    override fun getItemCount() = pictures.size

    abstract class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(pictureUi: PictureUi) {}

        class Success(view: View, private val pictureLoader: PictureLoader) :
            PictureViewHolder(view) {
            private val image = itemView.findViewById<ImageView>(R.id.picture)
            override fun bind(pictureUi: PictureUi) {
                pictureUi.map(object : PictureUi.UrlMapper {
                    override fun map(url: String) {
                        pictureLoader.fetchImage(url, image)
                        Log.d("load", url)
                    }
                })
            }
        }

        class Fail(view: View, private val retry: Retry) : PictureViewHolder(view) {
            private val message = itemView.findViewById<TextView>(R.id.messageTextView)
            private val button = itemView.findViewById<Button>(R.id.tryAgainButton)
            override fun bind(pictureUi: PictureUi) {
                pictureUi.map(object : PictureUi.UrlMapper {
                    override fun map(url: String) {
                        message.text = url
                    }
                })
                button.setOnClickListener {
                    retry.tryAgain()
                }
            }
        }

        class FullscreenProgress(view: View) : PictureViewHolder(view)
    }

    interface Retry {
        fun tryAgain()
    }

    class DiffUtilCallback(
        private val oldList: List<PictureUi>,
        private val newList: List<PictureUi>,
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