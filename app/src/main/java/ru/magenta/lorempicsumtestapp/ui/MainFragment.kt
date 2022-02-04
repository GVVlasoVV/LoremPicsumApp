package ru.magenta.lorempicsumtestapp.ui

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
import ru.magenta.lorempicsumtestapp.domain.PictureDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PicturesDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PicturesInteractor


class MainFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var pictureViewModel: PictureViewModel? = null

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

        val pictureInteractor = PicturesInteractor(
            repository,
            PicturesDataToDomainMapper(PictureDataToDomainMapper())
        )

        val mapper =
            PicturesDomainToUiMapper(ResourceProvider.Base(context!!), PictureDomainToUiMapper())

        val viewModelFactory = PicturesViewModelFactory(
            pictureInteractor,
            mapper,
            PictureCommunication(),
            PictureCache(context!!)
        )
        pictureViewModel = ViewModelProvider(this, viewModelFactory)[PictureViewModel::class.java]
        pictureViewModel?.startCache()
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
        val adapter = PictureAdapter(
            object : Retry {
                override fun tryAgain() = pictureViewModel!!.fetchPictures()
            },
            PictureLoader(context!!),
            object : FavoriteListener {
                override fun likeOrUnlike(id: Int) {
                    pictureViewModel?.likeOrUnlike(id)
                }
            }
        )
        recyclerView?.adapter = adapter
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        pictureViewModel?.observe(this, {
            adapter.update(it)
        })

        pictureViewModel?.fetchPictures()

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pictureViewModel?.addNewPicture()
                }
            }
        })
    }

    override fun onPause() {
        pictureViewModel?.saveState()
        super.onPause()
    }
}