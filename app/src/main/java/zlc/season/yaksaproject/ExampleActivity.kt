package zlc.season.yaksaproject

import android.annotation.SuppressLint
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
        viewModel.bindData(this, ::onChange)

        refresh.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    open fun onChange(data: List<ExampleViewModel.ExampleData>?) {
        refresh.isRefreshing = false
    }
}