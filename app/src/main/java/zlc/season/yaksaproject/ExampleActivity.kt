package zlc.season.yaksaproject

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_example.*

@SuppressLint("Registered")
open class ExampleActivity : AppCompatActivity() {
    lateinit var viewModel: ExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        viewModel = ViewModelProviders.of(this).get(ExampleViewModel::class.java)

        refresh.setOnRefreshListener {
            onRefresh()
        }
    }

    override fun onStart() {
        super.onStart()
        onRefresh()
    }

    private fun onRefresh() {
        viewModel.loadHeaderData()
        viewModel.loadData(true)
        viewModel.loadFooterData()
    }

    fun <T> LiveData<T>.observeX(block: (T) -> Unit) {
        this.observe(this@ExampleActivity, Observer {
            refresh.isRefreshing = false
            it?.let(block)
        })
    }
}
