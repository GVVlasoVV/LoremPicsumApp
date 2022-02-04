package ru.magenta.lorempicsumtestapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ru.magenta.lorempicsumtestapp.R
import ru.magenta.lorempicsumtestapp.core.Network
import ru.magenta.lorempicsumtestapp.core.PictureLoader
import ru.magenta.lorempicsumtestapp.data.Repository
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudDataSource
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudMapper
import ru.magenta.lorempicsumtestapp.ui.FavoriteListener
import ru.magenta.lorempicsumtestapp.ui.PictureCache
import ru.magenta.lorempicsumtestapp.ui.Retry

class FavoriteFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var viewModel: FavoriteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val network = Network()

        val infoService = network.pictureInfoService()

        val randomService = network.pictureRandomService()

        val cloudDataSource = PictureCloudDataSource(infoService, Gson())

        val repository = Repository(
            cloudDataSource,
            PictureCloudMapper(),
            randomService,
            PictureCache(context!!)
        )


        val viewModelFactory = FavoritesViewModelFactory(
            repository,
            FavoriteCommunication(),
            PictureCache(context!!)
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
        viewModel?.startCache()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(context)
        val adapter = FavoriteAdapter(
            object : Retry {
                override fun tryAgain() = viewModel!!.fetchFavorites()
            },
            PictureLoader(context!!),
            object : FavoriteListener {
                override fun likeOrUnlike(id: Int) {
                    viewModel?.likeOrUnlike(id)
                }
            }
        )
        recyclerView?.adapter = adapter
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        viewModel?.observe(this, {
            adapter.update(it)
        })

        viewModel?.fetchFavorites()
    }

    override fun onPause() {
        viewModel?.saveState()
        super.onPause()
    }
}