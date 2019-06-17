package zlc.season.yaksaproject

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_example.*
import kotlinx.android.synthetic.main.list_item.*
import zlc.season.yasha.linear

class LinearExampleActivity : ExampleActivity() {

    private val dataSource = TestDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        example_rv.linear(dataSource) {

            renderItem<HeaderData> {
                res(R.layout.header_item)
            }

            renderItem<ItemData> {
                res(R.layout.list_item)
                onBind { data ->
                    list_item_tv.text = data.title
                    root.setOnClickListener {
                        toast("Item ${data.index} Clicked")
                    }
                }
            }
        }
    }
}
