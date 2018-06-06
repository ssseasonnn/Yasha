package zlc.season.yaksaproject

import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.stagger
import java.util.*

class StaggerExampleActivity : ExampleActivity() {
    private val HEIGHTS = arrayOf(60, 80, 100, 120, 140, 160)

    override fun onChange(data: List<ExampleViewModel.ExampleData>?) {
        super.onChange(data)

        data?.let {
            example_rv.stagger {
                spanCount(3)

                item {
                    HeaderItem("This is a Header")
                }

                itemDsl(index = 0) {
                    staggerFullSpan(true)

                    xml(R.layout.header_item)
                    render {
                        it.header_item_tv.text = "This is a dsl Header"
                        it.setOnClickListener { toast("DSL Header Clicked") }
                    }
                }

                data.forEach { each ->
                    item {
                        ListItem(each.title, HEIGHTS[Random().nextInt(HEIGHTS.size)].px)
                    }
                }
            }
        }
    }

    private class HeaderItem(val title: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.header_item_tv.text = title
            view.setOnClickListener { }
        }

        override fun xml(): Int {
            return R.layout.header_item
        }

        override fun staggerFullSpan(): Boolean {
            return true
        }
    }

    private class ListItem(val str: String, val height: Int) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.layoutParams.height = height
            view.list_item_tv.text = str
            view.setOnClickListener {
                toast(view, "Clicked $position")
            }
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }
}