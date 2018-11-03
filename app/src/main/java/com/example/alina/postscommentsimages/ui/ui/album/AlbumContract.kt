package album

import model.Album
import util.IfProgressBar
import util.IfResult


interface AlbumContract {

    interface View : IfProgressBar, IfResult<List<Album>>

    interface Presenter {
        fun loadAlbums()
    }
}