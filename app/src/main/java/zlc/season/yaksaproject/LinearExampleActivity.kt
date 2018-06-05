package zlc.season.yaksaproject

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders.of
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_linear_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear

class LinearExampleActivity : AppCompatActivity() {

    lateinit var viewModel: ExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_example)

        viewModel = of(this).get(ExampleViewModel::class.java)

        viewModel.bindData(this) { list ->
            refresh.isRefreshing = false

            list?.let {
                rv_list.linear {
                    item {
                        HeaderItem("This is a Header")
                    }

                    itemDsl(index = 0) {
                        xml(R.layout.header_item)
                        render {
                            it.tv_header.text = "This is a dsl Header"
                            it.setOnClickListener { toast("DSL Header Clicked") }
                        }
                    }

                    list.forEach { each ->
                        itemDsl {
                            xml(R.layout.list_item)
                            render {
                                it.textView.text = each.title
                            }
                            renderX { position, it ->
                                it.setOnClickListener { toast("Clicked $position") }
                            }
                        }
                    }

                    item {
                        ListItem("This is item too!")
                    }
                }
            }
        }

        refresh.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    class HeaderItem(val title: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.tv_header.text = title
            view.setOnClickListener { }
        }

        override fun xml(): Int {
            return R.layout.header_item
        }
    }

    class ListItem(val str: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.textView.text = str
            view.setOnClickListener { }
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }

    private fun Activity.toast(msg: String) {
        Snackbar.make(findViewById<FrameLayout>(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT).show()
    }
}
