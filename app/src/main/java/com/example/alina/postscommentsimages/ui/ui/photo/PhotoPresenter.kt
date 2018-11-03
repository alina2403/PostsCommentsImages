package ui.album_photo

import android.util.Log
import com.example.alina.postscommentsimages.ui.StartApplication
import com.example.alina.postscommentsimages.ui.utils.Connection
import model.Photos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoPresenter(var view: PhotoContract.View?): PhotoContract.Presenter {

    override fun loadPhotosOfAlbum(id: Int?) {
        if(id == null) {
            Log.d("error","not found")
            return
        }
        if (!Connection.isNetworkOnline()) {
            return
        }
        view?.showProgress()
        StartApplication.service.loadAlbumPhotos(id).enqueue(object : Callback<List<Photos>> {
            override fun onResponse(call: Call<List<Photos>>?, response: Response<List<Photos>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    view?.onSuccess(response.body() ?: ArrayList())
                } else {
                    view?.onError("error")
                }
                view?.hideProgress()
            }

            override fun onFailure(call: Call<List<Photos>>?, t: Throwable?) {
                view?.onError("error")
                view?.hideProgress()
            }
        })
    }
}
