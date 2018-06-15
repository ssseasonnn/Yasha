package zlc.season.yaksaproject

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.error_item.view.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear
import zlc.season.yaksaproject.ExampleViewModel.ExampleData
import zlc.season.yaksaproject.ExampleViewModel.State

class LinearExampleActivity : ExampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.linear {

            renderPlaceholdersByDsl(List(10) { it }) {
                xml(R.layout.placeholder_item)
            }

            viewModel.itemData.observe(this@LinearExampleActivity, Observer {
                renderItemsByDsl(it!!) { item ->
                    xml(R.layout.list_item)
                    render { view ->
                        view.list_item_tv.text = item.title
                        view.setOnClickListener { toast("Item Clicked") }
                    }
                }
            })

            viewModel.state.observe(this@LinearExampleActivity, Observer { state ->
                when (state) {
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
            })
        }
    }

    override fun onChange(data: ExampleData?) {
        super.onChange(data)
//        data?.let { dataSource ->
//            example_rv.linear {
//
//                if (dataSource.isRefresh) {
//                    clearAll()
//
//                    renderHeaders(mutableListOf("Header1", "Header2")) {
//                        HeaderItem(it)
//                    }
//
//                    renderFootersByDsl(mutableListOf("Footer1", "Footer2")) { text ->
//                        xml(R.layout.header_item)
//                        render { view ->
//                            view.header_item_tv.text = text
//                            view.setOnClickListener { }
//                        }
//                    }
//
//                    renderItemsByDsl(dataSource.list) { item ->
//                        xml(R.layout.list_item)
//                        render { view ->
//                            view.list_item_tv.text = item.title
//                            view.setOnClickListener { toast("Item Clicked") }
//                        }
//                    }
//
//                    /**
//                     * Render a LOAD_MORE item that triggers load more action when it is displayed on the screen
//                     */
//                    renderStateItemByDsl {
//                        xml(R.layout.loading_item)
//                        onItemAttachWindow {
//                            viewModel.loadData()
//                        }
//                    }
//
//                } else {
//                    renderItemsByDsl(dataSource.list) { item ->
//                        xml(R.layout.list_item)
//                        render { view ->
//                            view.list_item_tv.text = item.title
//                            view.setOnClickListener { toast("Item Clicked") }
//                        }
//                    }
//                }
//
//
//                when (dataSource.state) {
//                    is State.Empty ->
//                        /**
//                         * render a NO_MORE state item
//                         */
//                        renderStateItemByDsl {
//                            xml(R.layout.empty_item)
//                        }
//
//                    is State.Error ->
//                        /**
//                         * render an ERROR state item
//                         */
//                        renderStateItemByDsl {
//                            xml(R.layout.error_item)
//                            render {
//                                it.retry.setOnClickListener {
//                                    viewModel.loadData()
//                                }
//                            }
//                        }
//                }
//            }
//        }
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
