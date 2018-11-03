package ui.album

import album.AlbumContract
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import kotlinx.android.synthetic.main.fragment_post.*
import model.Album
import ui.album_photo.PhotoFragment

class AlbumFragment : Fragment(), AlbumContract.View, AlbumAdapter.Listener {
    override fun onError(message: String?) {
    }

    private var presenter: AlbumContract.Presenter? = null
    private var albumAdapter: AlbumAdapter? = null
    private var list: List<Album> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        recyclerView.layoutManager = GridLayoutManager(context, 3) as RecyclerView.LayoutManager?
        albumAdapter = AlbumAdapter(ArrayList(), this)
        recyclerView.adapter = albumAdapter

        presenter = AlbumPresenter(this)
        presenter?.loadAlbums()

        swipeRefresh.setOnRefreshListener {
            presenter?.loadAlbums()
        }
    }

    override fun onSuccess(result: List<Album>) {
        this.list = result
        albumAdapter?.setAlbumList(result)
    }

    override fun onItemAtClicked(album: Album) {
        val intent = Intent(context, PhotoFragment::class.java)
        intent.putExtra("album", album)
        startActivity(intent)
    }

    override fun showProgress() {
        swipeRefresh?.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefresh?.isRefreshing = false
    }
}