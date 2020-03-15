package zlc.season.yaksaproject

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import kotlinx.android.synthetic.main.view_holder_footer.*
import kotlinx.android.synthetic.main.view_holder_header.*
import kotlinx.android.synthetic.main.view_holder_normal.*
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
                initScope {
                    set("test", Any())
                }

                res(R.layout.view_holder_normal)
                onBind {
                    val a = get<Any>("test")
                    println(a.toString())
                    tv_normal_content.text = data.toString()
                }
            }

            renderItem<HeaderItem> {
                res(R.layout.view_holder_header)
                onBind {
                    tv_header_content.text = data.toString()
                }
            }

            renderItem<FooterItem> {
                res(R.layout.view_holder_footer)
                onBind {
                    tv_footer_content.text = data.toString()
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