package zlc.season.yaksaproject

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_demo.*
import kotlinx.android.synthetic.main.view_holder_footer.*
import kotlinx.android.synthetic.main.view_holder_header.*
import kotlinx.android.synthetic.main.view_holder_normal.*
import kotlinx.android.synthetic.main.view_holder_state.*
import zlc.season.sange.FetchingState
import zlc.season.yasha.linear

class DemoActivity : AppCompatActivity() {
    private val demoViewModel by lazy {
        ViewModelProviders.of(this)[DemoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        recycler_view.linear(demoViewModel.dataSource) {
            renderItem<NormalItem> {
                res(R.layout.view_holder_normal)
                onBind {
                    tv_normal_content.text = it.toString()
                }
            }

            renderItem<HeaderItem> {
                res(R.layout.view_holder_header)
                onBind {
                    tv_header_content.text = it.toString()
                }
            }

            renderItem<FooterItem> {
                res(R.layout.view_holder_footer)
                onBind {
                    tv_footer_content.text = it.toString()
                }
            }

            renderItem<StateItem> {
                res(R.layout.view_holder_state)
                onBind {
                    tv_state_content.setOnClickListener { _ ->
                        it.retry()
                    }

                    when {
                        it.state == FetchingState.FETCHING -> {
                            state_loading.visibility = View.VISIBLE
                            tv_state_content.visibility = View.GONE
                        }
                        it.state == FetchingState.FETCHING_ERROR -> {
                            state_loading.visibility = View.GONE
                            tv_state_content.visibility = View.VISIBLE
                        }
                        it.state == FetchingState.DONE_FETCHING -> {
                            state_loading.visibility = View.GONE
                            tv_state_content.visibility = View.GONE
                        }
                        else -> {
                            state_loading.visibility = View.GONE
                            tv_state_content.visibility = View.GONE
                        }
                    }
                }
            }
        }


        swipe_refresh_layout.setOnRefreshListener {
            demoViewModel.refresh()
        }

        demoViewModel.refresh.observe(this, Observer {
            if (it == null) return@Observer
            swipe_refresh_layout.isRefreshing = it
        })
    }
}