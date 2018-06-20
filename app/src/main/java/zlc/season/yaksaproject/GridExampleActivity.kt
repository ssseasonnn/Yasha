package zlc.season.yaksaproject

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.YaksaState
import zlc.season.yaksa.grid
import zlc.season.yaksaproject.ExampleViewModel.State

class GridExampleActivity : ExampleActivity() {

    companion object {
        const val SPAN_COUNT = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.grid {
            spanCount(SPAN_COUNT)

            viewModel.headerData.observeX {
                renderHeaders(it, clear = true) { headerData ->
                    HeaderItem(headerData.header)
                }
            }

            viewModel.footerData.observeX {
                renderFootersByDsl(it, clear = true) { footerData ->
                    gridSpanSize(SPAN_COUNT)

                    xml(R.layout.header_item)
                    render { view ->
                        view.header_item_tv.text = footerData.footer
                        view.setOnClickListener { }
                    }
                }
            }

            viewModel.itemData.observeX {
                renderItemsByDsl(it.data, clear = it.isRefresh) { item ->
                    xml(R.layout.list_item)
                    render { view ->
                        view.list_item_tv.text = item.title
                        view.setOnClickListener { toast("Item Clicked") }
                    }
                }
            }

            viewModel.state.observeX {
                when (it) {
                    is State.Loading -> {
                        /**
                         * Render a LOAD_MORE item that triggers load more action when it is displayed on the screen
                         */
                        renderStateItemByDsl("loading") {
                            gridSpanSize(SPAN_COUNT)

                            xml(R.layout.loading_item)

                            onItemAttachWindow {
                                viewModel.loadData()
                            }
                        }
                    }

                    is State.Empty ->
                        /**
                         * render a NO_MORE state item
                         */
                        renderStateItem("empty") {
                            EmptyState()
                        }

                    is State.Error ->
                        /**
                         * render an ERROR state item
                         */
                        renderStateItem("error") {
                            ErrorState()
                        }
                }
            }
        }
    }

    private class ErrorState : YaksaState() {
        override fun renderStateItem(): YaksaItem {
            return object : YaksaItem {
                override fun render(position: Int, view: View) {

                }

                override fun xml(): Int {
                    return R.layout.error_item
                }

                override fun gridSpanSize(): Int {
                    return SPAN_COUNT
                }
            }
        }

        override fun isShowStateItem(): Boolean {
            return true
        }
    }

    private class EmptyState : YaksaState() {
        override fun renderStateItem(): YaksaItem {
            return object : YaksaItem {
                override fun render(position: Int, view: View) {

                }

                override fun xml(): Int {
                    return R.layout.empty_item
                }

                override fun gridSpanSize(): Int {
                    return SPAN_COUNT
                }
            }
        }

        override fun isShowStateItem(): Boolean {
            return true
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