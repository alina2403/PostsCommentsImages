package ui.post


import model.Comment
import model.Post
import model.WeatherInfo
import util.IfProgressBar
import util.IfResult

interface PostContract {

    interface View : IfProgressBar, IfResult<MutableList<Post>> {
        fun onWeatherResponse(result: WeatherInfo)
        fun onSelectComments(commentlist: ArrayList<Comment>)
    }

    interface Presenter {
        fun loadInformation()
        fun loadComments(list: MutableList<Any>?)
        fun getWeatherForCity(id: Int)
    }
}