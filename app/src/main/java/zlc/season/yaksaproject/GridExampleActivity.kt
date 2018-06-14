package zlc.season.yaksaproject

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.grid
import zlc.season.yaksa.gridWithPlaceholder

class GridExampleActivity : ExampleActivity() {

    companion object {
        const val SPAN_COUNT = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        example_rv.gridWithPlaceholder {
            spanCount(2)

            renderPlaceholdersByDsl(List(20) { it }) {
                xml(R.layout.placeholder_item)
            }
        }
    }

    override fun onChange(data: ExampleViewModel.ExampleData?) {
        super.onChange(data)
        data?.let { dataSource ->
            example_rv.grid {
                spanCount(SPAN_COUNT)

                if (dataSource.isRefresh) {
                    clearAll()

                    renderHeaders(mutableListOf("Header1", "Header2")) {
                        HeaderItem(it)
                    }

                    renderFooters(mutableListOf("Footer1", "Footer2")) {
                        HeaderItem(it)
                    }

                }

                renderItemsByDsl(dataSource.list) { item ->
                    xml(R.layout.list_item)

                    render { view ->
                        view.list_item_tv.text = item.title
                        view.setOnClickListener { toast("Item Clicked") }
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

        override fun gridSpanSize(): Int {
            return SPAN_COUNT
        }
    }
}