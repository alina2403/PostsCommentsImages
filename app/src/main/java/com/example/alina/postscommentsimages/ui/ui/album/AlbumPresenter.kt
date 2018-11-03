package ui.album



import album.AlbumContract
import com.example.alina.postscommentsimages.ui.StartApplication
import com.example.alina.postscommentsimages.ui.utils.Connection
import model.Album
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AlbumPresenter(var view: AlbumContract.View?) : AlbumContract.Presenter {

    override fun loadAlbums() {
        if (!Connection.isNetworkOnline()) {
            view?.onError("error")
            return
        }
        view?.showProgress()
        StartApplication.service.loadAlbums().enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>?, response: Response<List<Album>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    val list = response.body() ?: ArrayList()
                    view?.onSuccess(list)
                } else {
                    view?.onError("error")
                }
                view?.hideProgress()
            }

            override fun onFailure(call: Call<List<Album>>?, t: Throwable?) {
                view?.onError("error")
                view?.hideProgress()
            }
        })
    }
}