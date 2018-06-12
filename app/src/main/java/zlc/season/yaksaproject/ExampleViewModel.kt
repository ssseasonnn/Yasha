package zlc.season.yaksaproject

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val exampleData: MutableLiveData<List<ExampleData>> = MutableLiveData()

    fun bindData(owner: LifecycleOwner, onChange: (t: List<ExampleData>?) -> Unit) {
        exampleData.observe(owner, Observer {
            onChange(it)
        })
    }

    fun loadData() {
        thread(start = true) {
            Thread.sleep(500)
            val newData = mutableListOf<ExampleData>()
            for (i in 0..5) {
                newData.add(ExampleData("this is item $i"))
            }
            exampleData.update(newData)
        }
    }

    data class ExampleData(
            val title: String
    )
}