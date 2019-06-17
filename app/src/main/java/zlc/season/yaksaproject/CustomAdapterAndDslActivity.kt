//package zlc.season.yaksaproject
//
//import android.os.Bundle
//import kotlinx.android.synthetic.main.activity_example.*
//import kotlinx.android.synthetic.main.error_item.view.*
//import kotlinx.android.synthetic.main.header_item.view.*
//import kotlinx.android.synthetic.main.list_item.view.*
//import zlc.season.yasha.YaksaPlaceholderAdapter
//import zlc.season.yasha.YaksaPlaceholderDsl
//import zlc.season.yasha.linear
//import zlc.season.yaksaproject.ExampleViewModel.State
//
//class CustomAdapterAndDslActivity : ExampleActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        example_rv.linear(::YaksaPlaceholderAdapter, ::YaksaPlaceholderDsl) {
//
//            renderPlaceholdersByDsl(List(20) { it }) {
//                res(R.layout.placeholder_item)
//            }
//
//            viewModel.headerData.observeX {
//                renderHeadersByDsl(it, clear = true) { headerData ->
//                    res(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = title
//                        view.setOnClickListener { }
//                    }
//                }
//            }
//
//            viewModel.footerData.observeX {
//                renderFootersByDsl(it, clear = true) { footerData ->
//                    res(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = footerData.footer
//                        view.setOnClickListener { }
//                    }
//                }
//            }
//
//            viewModel.itemData.observeX {
//                renderItemsByDsl(it.data, clear = it.isRefresh) { item ->
//                    res(R.layout.list_item)
//                    render { view ->
//                        view.list_item_tv.text = item.title
//                        view.setOnClickListener { toast("Item Clicked") }
//                    }
//                }
//            }
//
//            viewModel.state.observeX {
//                when (it) {
//                    is State.Loading -> {
//                        /**
//                         * Render a LOAD_MORE item that triggers load more action when it is displayed on the screen
//                         */
//                        renderStateItemByDsl("loading") {
//                            res(R.layout.loading_item)
//                            onItemAttachWindow {
//                                viewModel.loadData()
//                            }
//                        }
//                    }
//
//                    is State.Empty ->
//                        /**
//                         * render a NO_MORE state item
//                         */
//                        renderStateItemByDsl("empty") {
//                            res(R.layout.empty_item)
//                        }
//
//                    is State.Error ->
//                        /**
//                         * render an ERROR state item
//                         */
//                        renderStateItemByDsl("error") {
//                            res(R.layout.error_item)
//                            render {
//                                it.retry.setOnClickListener {
//                                    viewModel.loadData()
//                                }
//                            }
//                        }
//                }
//            }
//        }
//    }
//}