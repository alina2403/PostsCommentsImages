package  ui.post_comments

import android.os.Bundle
import BaseActivity
import kotlinx.android.synthetic.main.fragment_post.*
import model.Comment
import model.Post
import kotlinx.android.synthetic.main.activity_comments.*

class CommentActivity : BaseActivity(), CommentContract.View { open class BaseActivity {}

    private var presenter: CommentContract.Presenter? = null
    private var adapter: CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        adapter = CommentAdapter(ArrayList())
        recyclerView.adapter = adapter
        presenter = CommentPresenter(this)
        val post = intent?.getParcelableExtra<Post>("post")
        presenter?.loadComments(post?.id)

        postTitle.text = post?.title
    }

    override fun onSuccess(result: List<Comment>) {
        adapter?.setPostCommentList(result)    } }