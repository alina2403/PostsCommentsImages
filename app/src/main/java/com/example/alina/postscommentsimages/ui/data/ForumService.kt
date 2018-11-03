package data

import model.*
import model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumService {

    @GET("posts")
    fun loadPosts(): Call<MutableList<Post>>

    @GET("comments")
    fun loadComments(): Call<List<Comment>>

    @GET("posts/{postId}/comments")
    fun loadPostComments(@Path("postId") postid: Int): Call<List<Comment>>

    @GET("albums")
    fun loadAlbums(): Call<List<Album>>

    @GET("albums/{albumId}/photos")
    fun loadAlbumPhotos(@Path("albumId") albumId: Int): Call<List<Photos>>

    @GET("data/2.5/weather")
    fun getWeatherForCity(@Query("id") cityId: Int, @Query("appid") token: String): Call<WeatherInfo>
}