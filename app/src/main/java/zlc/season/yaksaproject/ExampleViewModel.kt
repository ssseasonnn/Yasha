package zlc.season.yaksaproject

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val exampleData: MutableLiveData<ExampleData> = MutableLiveData()
    private val pageSize = 5
    private var start = 0

    private val testData = mutableListOf<ItemData>()

    private var isLoading = false

    init {
        for (i in 0 until 50) {
            testData.add(ItemData("this is item $i"))
        }
    }

    fun observerLiveData(owner: LifecycleOwner, onChange: (t: ExampleData?) -> Unit) {
        exampleData.observe(owner, Observer {
            onChange(it)
        })
    }

    fun loadData(isRefresh: Boolean = false) {
        if (isRefresh) {
            start = 0
        }

        if (isLoading) {
            return
        }

        if (start == 20) {
            exampleData.update(ExampleData(emptyList(), false, State.Error()))
            start += pageSize
            return
        }
        if (start >= 25) {
            exampleData.update(ExampleData(emptyList(), false, State.Empty()))
            return
        }

        thread(start = true) {
            if (start >= 25) return@thread

            isLoading = true
            Thread.sleep(1500)
            exampleData.update(ExampleData(testData.slice(IntRange(start, start + pageSize - 1)), isRefresh))
            start += pageSize

            isLoading = false
        }
    }

    data class ExampleData(
            val list: List<ItemData>,
            val isRefresh: Boolean = true,
            val state: State = State.None()
    )

    data class ItemData(
            val title: String
    )

    sealed class State {
        class None : State()
        class Error : State()
        class Empty : State()
    }
}