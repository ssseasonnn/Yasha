package zlc.season.yaksaproject

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val testData = mutableListOf<ItemData>()
    private val pageSize = 5
    private var start = 0

    private var isLoading = false

    val itemData: MutableLiveData<List<ItemData>> = MutableLiveData()
    val state: MutableLiveData<State> = MutableLiveData()

    init {
        for (i in 0 until 50) {
            testData.add(ItemData("this is item $i"))
            state.update(State.Loading())
        }
    }

    fun loadData(isRefresh: Boolean = false) {
        if (isRefresh) {
            start = 0
        }

        if (isLoading) {
            return
        }

        if (start == 20) {
            state.update(State.Error())
            start += pageSize
            return
        }
        if (start >= 25) {
            state.update(State.Empty())
            return
        }

        thread(start = true) {
            if (start >= 25) return@thread

            isLoading = true
            Thread.sleep(1500)

            itemData.update(testData.slice(IntRange(start, start + pageSize - 1)))

            start += pageSize

            isLoading = false
        }
    }

    data class ExampleData(
            val list: List<ItemData>,
            val isRefresh: Boolean = true,
            val state: State = State.Loading()
    )

    data class ItemData(
            val title: String
    )

    sealed class State {
        class Loading : State()
        class Error : State()
        class Empty : State()
    }
}