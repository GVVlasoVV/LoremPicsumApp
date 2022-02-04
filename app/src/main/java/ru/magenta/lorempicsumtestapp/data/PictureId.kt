package ru.magenta.lorempicsumtestapp.data

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.magenta.lorempicsumtestapp.data.cloud.PictureRandomService

class PictureId(private val service: PictureRandomService) {
    suspend fun fetchId(): Int {
        val res = CompletableDeferred<Int>()
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("random", "oi")
            service.fetchRandomPicture().enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val id = response.headers()["Picsum-Id"]!!.toInt()
                    Log.e("id", id.toString())
                    res.complete(id)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("callback error", t.toString())
                    throw t
                }
            })
        }
        return res.await()
    }
}