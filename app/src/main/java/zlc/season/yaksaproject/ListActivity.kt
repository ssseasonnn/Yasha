package zlc.season.yaksaproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear

class ListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val headerData = mutableListOf<String>()
        headerData.add("This is header 1")
        headerData.add("This is header 2")


        val data = mutableListOf<String>()
        for (i in 0..50) {
            data.add("this is item $i")
        }

        rv_data.linear {
            headerData.forEach {
                item {
                    HeaderItem(it)
                }
            }

            itemDsl {
                xml(R.layout.header_item)
                render {
                    it.title.text = "This is a dsl header"
                }
            }

            data.forEach {
                item {
                    ListItem(it)
                }
            }
        }
    }


    class HeaderItem(val title: String) : YaksaItem {
        override fun render(view: View) {
            view.title.text = title
        }

        override fun xml(): Int {
            return R.layout.header_item
        }
    }

    class ListItem(val str: String) : YaksaItem {
        override fun render(view: View) {
            view.textView.text = str
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }
}
