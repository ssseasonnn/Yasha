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

        data?.let { dataSource ->
            example_rv.stagger {
                spanCount(3)

                renderHeaders(mutableListOf("Header1", "Header2", "Header3")) {
                    HeaderItem(it)
                }

                renderItems(dataSource) { item ->
                    ListItem(item.title, HEIGHTS[Random().nextInt(HEIGHTS.size)].px)
                }

                renderFooters(mutableListOf("Footer1", "Footer2")) {
                    HeaderItem(it)
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