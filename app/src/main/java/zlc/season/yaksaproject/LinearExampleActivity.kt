package zlc.season.yaksaproject

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders.*
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
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
            list?.let {
                pb_loading.visibility = GONE

                rv_list.linear {
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
                }
            }
        }


        btn_add_header.setOnClickListener {
            rv_list.linear(clear = false) {
                itemDsl(index = 0) {
                    xml(R.layout.header_item)
                    render {
                        it.title.text = "this is a header"
                        it.setOnClickListener { toast("Header Clicked") }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
        viewModel.loadHeader()
    }

    private fun Activity.toast(msg: String) {
        Snackbar.make(findViewById<FrameLayout>(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT).show()
    }

    class HeaderItem(val title: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.title.text = title
        }

        override fun xml(): Int {
            return R.layout.header_item
        }
    }

    class ListItem(val str: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.textView.text = str
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }
}
