package zlc.season.yaksaproject

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.nested_header_item.view.*
import kotlinx.android.synthetic.main.nested_header_layout.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear


class NestedExampleActivity : ExampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        example_rv.linear {

//            if (dataSource.isRefresh) {
//                clearAll()
//
//                renderHeadersByDsl(mutableListOf("Header1", "Header2")) {
//                    xml(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = it
//                    }
//                }
//
//                renderFootersByDsl(mutableListOf("Footer1", "Footer2")) {
//                    xml(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = it
//                    }
//                }
//
//                renderHeaders(mutableListOf(dataSource)) {
//                    NestedHeaderItem(it)
//                }
//            }
//
//            renderItemsByDsl(dataSource.list) { item ->
//                xml(R.layout.list_item)
//
//                render { view ->
//                    view.list_item_tv.text = item.title
//                    view.setOnClickListener { toast("Item Clicked") }
//                }
//            }
        }
    }

    private class NestedHeaderItem(val data: ExampleViewModel.ExampleData) : YaksaItem {
        var scrollState = ScrollState(0, 0)

        override fun xml(): Int {
            return R.layout.nested_header_layout
        }

        override fun render(position: Int, view: View) {

            view.nested_header_rv.linear {
                orientation(HORIZONTAL)

                renderItemsByDsl(data.list) { item ->
                    xml(R.layout.nested_header_item)

                    render { view ->
                        view.nested_header_item_tv.text = item.title
                        view.setOnClickListener { toast(view, "Item Clicked") }
                    }
                }
            }
        }

        override fun onItemAttachWindow(position: Int, view: View) {
            super.onItemAttachWindow(position, view)
            resetScrollState(view.nested_header_rv, scrollState)
        }

        override fun onItemDetachWindow(position: Int, view: View) {
            super.onItemDetachWindow(position, view)
            scrollState = saveScrollState(view.nested_header_rv)
        }


        private fun saveScrollState(recyclerView: RecyclerView): ScrollState {
            var offset = 0
            var position = 0

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val topView = layoutManager.getChildAt(0)
            if (topView != null) {
                offset = topView.top
                position = layoutManager.getPosition(topView)
            }
            return ScrollState(offset, position)
        }


        private fun resetScrollState(recyclerView: RecyclerView, scrollState: ScrollState) {
            val (offset, position) = scrollState
            if (recyclerView.layoutManager != null && position >= 0) {
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, offset)
            }
        }
    }

    data class ScrollState(
            val offset: Int,
            val position: Int
    )
}