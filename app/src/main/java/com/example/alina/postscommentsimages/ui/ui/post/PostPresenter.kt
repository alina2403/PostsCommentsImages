package ui.post

import android.util.Log
import com.example.alina.postscommentsimages.ui.StartApplication
import com.example.alina.postscommentsimages.ui.utils.Connection
import model.Comment
import model.Post
import model.WeatherInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostPresenter(val view: PostContract.View?) : PostContract.Presenter {
    override fun loadInformation() {
        if (!Connection.isNetworkOnline()) {
            Log.d("Error","Error")
            view?.onError("error")
            return
        }
        Log.d("Success","Connected")
        view?.showProgress()
        StartApplication.service.loadPosts().enqueue(object : Callback<MutableList<Post>> {
            override fun onResponse(call: Call<MutableList<Post>>?, response: Response<MutableList<Post>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    val list = response.body() ?: ArrayList()
                    view?.onSuccess(list)
                    Log.d("Success",list.size.toString())
                } else {
                    Log.d("Error", "no connection")
                    view?.onError("error")
                }
                view?.hideProgress()
            }

            override fun onFailure(call: Call<MutableList<Post>>?, t: Throwable?) {
                view?.onError("error")
                view?.hideProgress()
            }
        })
    }

    override fun loadComments(list: MutableList<Any>?) {
        if (!Connection.isNetworkOnline()) {
            view?.onError("error")
            return
        }
        view?.showProgress()
        StartApplication.service.loadComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    selectPostComments(list, response.body()!!)
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

    private fun selectPostComments(list: MutableList<Any>?, listOfComments: List<Comment>) {
        if(list == null)
            return
        val commentlist = ArrayList<Comment>()
        for (comment in listOfComments) {
            for (post in list) {
                if (post is WeatherInfo)
                    continue
                else {
                    post as Post
                    if (post.id == comment.postId)
                        commentlist.add(comment)
                }
            }
        }
        view?.onSelectComments(commentlist)
    }

    override fun getWeatherForCity(id: Int) {
        StartApplication.weatherService.getWeatherForCity(id, "b1b15e88fa797225412429c1c50c122a1")
                .enqueue(object : Callback<WeatherInfo> {
                    override fun onResponse(call: Call<WeatherInfo>?, response: Response<WeatherInfo>?) {
                        if (response!!.isSuccessful && response.body() != null) {
                            view?.onWeatherResponse(response.body()!!)
                        } else {
                            Log.d("Error","Error")
                        }
                    }

                    override fun onFailure(call: Call<WeatherInfo>?, t: Throwable?) {
                        Log.d("Error","Error")}

                })
    }
}