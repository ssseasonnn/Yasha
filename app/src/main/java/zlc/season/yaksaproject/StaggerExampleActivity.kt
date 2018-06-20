package zlc.season.yaksaproject

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.error_item.view.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.stagger
import zlc.season.yaksaproject.ExampleViewModel.State
import java.util.*

class StaggerExampleActivity : ExampleActivity() {
    private val HEIGHTS = arrayOf(60, 80, 100, 120, 140, 160)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.stagger {
            spanCount(3)

            viewModel.headerData.observeX {
                renderHeaders(it, clear = true) { headerData ->
                    HeaderItem(headerData.header)
                }
            }

            viewModel.footerData.observeX {
                renderFootersByDsl(it, clear = true) { footerData ->
                    staggerFullSpan(true)
                    xml(R.layout.header_item)
                    render { view ->
                        view.header_item_tv.text = footerData.footer
                        view.setOnClickListener { }
                    }
                }
            }

            viewModel.itemData.observeX {
                renderItems(it.data, clear = it.isRefresh) { item ->
                    ListItem(item.title, HEIGHTS[Random().nextInt(HEIGHTS.size)].px)
                }
            }

            viewModel.state.observeX {
                when (it) {
                    is State.Loading -> {
                        /**
                         * Render a LOAD_MORE item that triggers load more action when it is displayed on the screen
                         */
                        renderStateItemByDsl("loading") {
                            staggerFullSpan(true)

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
                            staggerFullSpan(true)
                            xml(R.layout.empty_item)
                        }

                    is State.Error ->
                        /**
                         * render an ERROR state item
                         */
                        renderStateItemByDsl("error") {
                            staggerFullSpan(true)
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