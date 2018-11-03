package ui.album_photo


import model.Photos
import util.IfProgressBar
import util.IfResult


interface PhotoContract {
    interface View : IfProgressBar, IfResult<List<Photos>>

    interface Presenter{
        fun loadPhotosOfAlbum(id: Int?)
    }
}