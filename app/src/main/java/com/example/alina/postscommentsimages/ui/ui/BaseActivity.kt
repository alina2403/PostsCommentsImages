import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import model.Comment
@Suppress("DEPRECATION")

open class BaseActivity : AppCompatActivity() {
    var progressBar: ProgressDialog? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    open fun showProgress() {
        this.runOnUiThread {
            progressBar == null
            if (progressBar == null && !isFinishing) {
                progressBar = ProgressDialog(this)
                progressBar?.setMessage(getString(R.string.loading))
                progressBar?.setCanceledOnTouchOutside(false)
                progressBar?.show()
            }
        }
    }

    open fun hideProgress() {
        this.runOnUiThread {
            if (progressBar != null && progressBar?.isShowing!!) {
                progressBar!!.dismiss()
                progressBar = null
            }
        }
    }

    open fun onError(message: String?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgress()
    }

}