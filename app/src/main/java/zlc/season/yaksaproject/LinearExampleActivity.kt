package zlc.season.yaksaproject

import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.YaksaState
import zlc.season.yaksa.linear
import zlc.season.yaksaproject.ExampleViewModel.ExampleData

class LinearExampleActivity : ExampleActivity() {

    override fun onChange(data: ExampleData?) {
        super.onChange(data)
        data?.let { dataSource ->
            example_rv.linear {

                if (dataSource.isRefresh) {
                    clearAll()

                    renderHeaders(mutableListOf("Header1", "Header2")) {
                        HeaderItem(it)
                    }

                    renderFootersByDsl(mutableListOf("Footer1", "Footer2")) { text ->
                        xml(R.layout.header_item)
                        render { view ->
                            view.header_item_tv.text = text
                            view.setOnClickListener { }
                        }
                    }

                    setStateByDsl {
                        xml(R.layout.loading_item)
                        onItemAttachWindow {
                            viewModel.loadNextPage()
                        }
                    }
                }

                if (dataSource.isNoMore) {
                    setStateByDsl {
                        xml(R.layout.empty_item)
                    }
                }

                if (dataSource.isLoadMoreError) {

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
    }
}
