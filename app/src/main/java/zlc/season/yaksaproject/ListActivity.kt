package zlc.season.yaksaproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.title_item.view.*
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
        for (i in 0..10) {
            data.add("this is item $i")
        }

        rv_data.linear {
            headerData.forEach {
                header {
                    HeaderItem(it)
                }
            }

            headerDsl {
                xml(R.layout.title_item)
                render {
                    it.title.text = "test"
                }
            }

            data.forEach {
                item {
                    NewItem(it)
                }
            }
        }
    }


    class HeaderItem(val title: String) : YaksaItem {
        override fun render(view: View) {
            view.title.text = title
        }

        override fun xml(): Int {
            return R.layout.title_item
        }
    }

    class NewItem(val str: String) : YaksaItem {

        override fun render(view: View) {
            view.textView.text = str
        }

        override fun xml(): Int {
            return R.layout.list_item
        }
    }
}
