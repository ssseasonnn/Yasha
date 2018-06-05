package zlc.season.yaksaproject

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val exampleData: MutableLiveData<List<ExampleData>> = MutableLiveData()
    private val headerData: MutableLiveData<List<HeaderData>> = MutableLiveData()

    fun bindData(owner: LifecycleOwner, onChange: (t: List<ExampleData>?) -> Unit) {
        exampleData.observe(owner, Observer {
            onChange(it)
        })
    }

    fun bindHeader(owner: LifecycleOwner, onChange: (t: List<HeaderData>?) -> Unit) {
        headerData.observe(owner, Observer {
            onChange(it)
        })
    }

    fun loadData() {
        thread(start = true) {
            Thread.sleep(2000)
            val newData = mutableListOf<ExampleData>()
            for (i in 0..50) {
                newData.add(ExampleData("this is item $i"))
            }
            exampleData.update(newData)
        }
    }

    fun loadHeader() {
        thread(start = true) {
            Thread.sleep(4000)
            val headers = mutableListOf<HeaderData>()
            for (i in 0..1) {
                headers.add(HeaderData("this is header $i"))
            }
            headerData.update(headers)
        }
    }

    data class ExampleData(
            val title: String
    )

    data class HeaderData(
            val title: String
    )
}