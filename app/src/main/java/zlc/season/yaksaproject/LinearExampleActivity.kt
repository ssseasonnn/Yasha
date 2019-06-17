package zlc.season.yaksaproject

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.list_item.*
import zlc.season.yasha.linear

class LinearExampleActivity : ExampleActivity() {

    val dataSource = TestDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.linear(dataSource) {

            renderItem<ExampleViewModel.ItemData> {
                res(R.layout.list_item)
                onBind {
                    list_item_tv.text = it.title
                    root.setOnClickListener { toast("Item Clicked") }
                }
            }
        }
    }

//    private class HeaderItem(val title: String) : YaksaItem {
//        override fun render(position: Int, view: View) {
//            view.header_item_tv.text = title
//            view.setOnClickListener { }
//        }
//
//        override fun res(): Int {
//            return R.layout.header_item
//        }
//    }
}
