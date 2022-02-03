package ru.magenta.lorempicsumtestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ru.magenta.lorempicsumtestapp.core.Network
import ru.magenta.lorempicsumtestapp.core.PictureLoader
import ru.magenta.lorempicsumtestapp.data.Repository
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudDataSource
import ru.magenta.lorempicsumtestapp.data.cloud.PictureCloudMapper
import ru.magenta.lorempicsumtestapp.domain.PictureDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PicturesDataToDomainMapper
import ru.magenta.lorempicsumtestapp.domain.PicturesInteractor
import ru.magenta.lorempicsumtestapp.ui.*


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
            randomService
        )
        val pictureInteractor = PicturesInteractor(
            repository,
            PicturesDataToDomainMapper(PictureDataToDomainMapper())
        )
        val mapper =
            PicturesDomainToUiMapper(ResourceProvider.Base(context!!), PictureDomainToUiMapper())
        val viewModelFactory = ViewModelFactory(pictureInteractor, mapper, PictureCommunication())
        pictureViewModel = ViewModelProvider(this, viewModelFactory)[PictureViewModel::class.java]
        pictureViewModel
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
            object : PictureAdapter.Retry {
                override fun tryAgain() = pictureViewModel!!.fetchPictures()
            },
            // TODO: 30.01.2022 make width & height dependent on screen size
            PictureLoader(context!!, 600, 400),
            object : PictureAdapter.Like {
                override fun setFavorite() {
                    Toast.makeText(context, "like", Toast.LENGTH_SHORT).show()
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    private fun getHeight() {

    }
}