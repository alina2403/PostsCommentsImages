package ui.post_comments


import model.Comment
import util.IfProgressBar
import util.IfResult


interface CommentContract {
    interface View : IfProgressBar, IfResult<List<Comment>>

    interface Presenter {
        fun loadComments(postId: Int?)
    }
}