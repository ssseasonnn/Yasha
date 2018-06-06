package zlc.season.yaksaproject

import android.support.v7.widget.RecyclerView.*
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

        override fun xml(): Int {
            return R.layout.nested_header_layout
        }
    }
}