package zlc.season.yaksaproject

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.error_item.view.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.*
import zlc.season.yaksaproject.ExampleViewModel.State

class LinearExampleActivity : ExampleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.linear(enableDiff = true) {

            viewModel.headerData.observeX {
                renderHeaders(it, clear = true) { headerData ->
                    HeaderItem(headerData.header)
                }
            }

            viewModel.footerData.observeX {
                renderFootersByDsl(it, clear = true) { footerData ->
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
                        renderStateItemByDsl("empty") {
                            xml(R.layout.empty_item)
                        }

                    is State.Error ->
                        /**
                         * render an ERROR state item
                         */
                        renderStateItemByDsl("error") {
                            xml(R.layout.error_item)
                            render {
                                it.retry.setOnClickListener {
                                    viewModel.loadData()
                                }
                            }
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
