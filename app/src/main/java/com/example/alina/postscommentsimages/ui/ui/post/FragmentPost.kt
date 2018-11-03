package ui.post

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_post.*
import model.Comment
import model.Post
import model.WeatherInfo
import ui.post_comments.CommentAdapter
import ui.post_comments.CommentActivity

class FragmentPost : Fragment(), PostContract.View, PostAdapter.Listener {
    private var presenter: PostContract.Presenter? = null
    private var postAdapter: PostAdapter? = null
    private var commentsAdapter: CommentAdapter? = null
    private var list: MutableList<Any> = ArrayList()
    private var isSelectedElements = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        postAdapter = PostAdapter(list, this)
        recyclerView.adapter = postAdapter
        presenter = PostPresenter(this)
        presenter?.loadInformation()
        getWeatherFor()

        swipeRefresh.setOnRefreshListener {
            presenter?.loadInformation()
            getWeatherFor()
        }
    }

    override fun onPostClicked(post: Post) {
        val intent = Intent(context, CommentActivity::class.java)
        intent.putExtra("post", post)
        startActivity(intent)
    }

    override fun onWeatherClicked() {
        startActivity(Intent(context, WebViewActivity::class.java))
    }

    override fun onSuccess(result: MutableList<Post>) {
        this.list.addAll(result)
        postAdapter?.setPostList(this.list)
        recyclerView.adapter = postAdapter
    }

    override fun onError(message: String?) {
        FileLog.showError(context, message)
    }

    override fun showProgress() {
        swipeRefresh?.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefresh?.isRefreshing = false
        isSelectedElements = false
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.extract_elements, menu)
    }

    override fun onWeatherResponse(result: WeatherInfo) {
        postAdapter?.addAtRandom(result)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.nElement -> {
                postAdapter?.setPostList(Constant.selectTenElements(list))
                isSelectedElements = true
            }
            R.id.nComments -> {
                if(isSelectedElements){
                    presenter?.loadComments(postAdapter?.list)
                }else
                    FileLog.showError(context, getString(R.string.error_select_first))
                isSelectedElements = false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getWeatherFor() {
        val bishkekId = 152856
        presenter?.getWeatherForCity(bishkekId)
    }

    override fun onSelectComments(commentlist: ArrayList<Comment>) {
        if(commentsAdapter == null)
            commentsAdapter = CommentAdapter(commentlist)
        else
            commentsAdapter?.setPostCommentList(commentlist)
        recyclerView.adapter = commentsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        postAdapter = null
        commentsAdapter = null
    }
}