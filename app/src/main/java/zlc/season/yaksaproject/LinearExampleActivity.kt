package zlc.season.yaksaproject

import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear

class LinearExampleActivity : ExampleActivity() {

    override fun onChange(data: List<ExampleViewModel.ExampleData>?) {
        super.onChange(data)
        data?.let { dataSource ->
            example_rv.linear {

                renderHeaders(mutableListOf("Header1", "Header2", "Header3")) {
                    HeaderItem(it)
                }

                renderItemsByDsl(dataSource) { item ->
                    xml(R.layout.list_item)
                    render { view ->
                        view.list_item_tv.text = item.title
                        view.setOnClickListener { toast("Item Clicked") }
                    }
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
    }

    private class ListItem(val str: String) : YaksaItem {
        override fun render(position: Int, view: View) {
            view.list_item_tv.text = str
            view.setOnClickListener { }
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }
}
