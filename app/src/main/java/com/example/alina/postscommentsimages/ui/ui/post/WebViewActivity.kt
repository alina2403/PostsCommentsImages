package ui.post

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import ui.post_comments.CommentActivity
import BaseActivity
import R

class WebViewActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = "google.com"
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }
}



