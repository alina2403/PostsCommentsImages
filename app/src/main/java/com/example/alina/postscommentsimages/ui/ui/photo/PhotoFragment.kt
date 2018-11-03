package ui.album_photo

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.GridLayoutManager
import model.Album
import model.Photos
import com.example.alina.postscommentsimages.R
import BaseActivity
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_post.*

class PhotoFragment : BaseActivity(), PhotoContract.View, PhotoAdapter.Listener {
    private var presenter: PhotoContract.Presenter? = null
    private var adapter: PhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val album = intent.getParcelableExtra<Album>("album") ?: return
        recyclerView.layoutManager = GridLayoutManager(this, 3) as RecyclerView.LayoutManager?

        adapter = PhotoAdapter(ArrayList(), this)
        recyclerView.adapter = adapter

        presenter = PhotoPresenter(this)
        presenter?.loadPhotosOfAlbum(album.id)

        swipeRefresh.setOnRefreshListener {
            presenter?.loadPhotosOfAlbum(album.id)
        }
    }

    override fun onSuccess(result: List<Photos>) {
        adapter?.setAlbumPhotoList(result)
    }

    override fun onItemClickedAt(photo: Photos) {
        val intent = Intent(this, FullScreen::class.java)
        intent.putExtra("url", photo.url)
        startActivity(intent)
    }

    override fun showProgress() {
        super.showProgress()
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        super.hideProgress()
        swipeRefresh.isRefreshing = false
    }
}