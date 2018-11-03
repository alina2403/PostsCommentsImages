package ui.post_comments


import android.util.Log
import com.example.alina.postscommentsimages.ui.StartApplication
import com.example.alina.postscommentsimages.ui.utils.Connection
import model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentPresenter(var view: CommentContract.View?) : CommentContract.Presenter {

    override fun loadComments(postId: Int?) {
        if(postId == null) {
            Log.d("Error","Not found")
            return
        }
        if (!Connection.isNetworkOnline()) {
            view?.onError("error")
            return
        }
        view?.showProgress()
        StartApplication.service.loadPostComments(postId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    view?.onSuccess(response.body() ?: ArrayList())
                } else {
                    view?.onError("error")
                }
                view?.hideProgress()
            }

            override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
                view?.onError("error")
                view?.hideProgress()
            }
        })
    }
}