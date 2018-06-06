package zlc.season.yaksaproject

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.nested_header_item.view.*
import kotlinx.android.synthetic.main.nested_header_layout.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear


class NestedExampleActivity : ExampleActivity() {

    override fun onChange(data: List<ExampleViewModel.ExampleData>?) {
        super.onChange(data)
        data?.let {
            example_rv.linear {
                itemDsl(index = 0) {
                    xml(R.layout.header_item)
                    render {
                        it.header_item_tv.text = "This is a dsl Header"
                        it.setOnClickListener { toast("DSL Header Clicked") }
                    }
                }

                item {
                    NestedHeaderItem(data)
                }

                data.forEach { each ->
                    itemDsl {
                        xml(R.layout.list_item)
                        render {
                            it.list_item_tv.text = each.title
                        }
                        renderX { position, it ->
                            it.setOnClickListener { toast("Clicked $position") }
                        }
                    }
                }
            }
        }
    }

    private class NestedHeaderItem(val data: List<ExampleViewModel.ExampleData>) : YaksaItem {
        var scrollState = ScrollState(0, 0)

        override fun xml(): Int {
            return R.layout.nested_header_layout
        }

        override fun render(position: Int, view: View) {

            view.nested_header_rv.linear {

                orientation(HORIZONTAL)

                data.forEach { each ->
                    itemDsl {
                        xml(R.layout.nested_header_item)
                        render {
                            it.nested_header_item_tv.text = each.title
                        }
                        renderX { position, it ->
                            it.setOnClickListener { toast(view, "This is nested item $position") }
                        }
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