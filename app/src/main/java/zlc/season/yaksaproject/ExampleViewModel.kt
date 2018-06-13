package zlc.season.yaksaproject

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val exampleData: MutableLiveData<ExampleData> = MutableLiveData()
    private var page = 0
    private val pageSize = 5

    private var isNoMore = false
    private var isLoadMoreError = false

    fun bindData(owner: LifecycleOwner, onChange: (t: ExampleData?) -> Unit) {
        exampleData.observe(owner, Observer {
            onChange(it)
        })
    }

    fun loadData() {
        thread(start = true) {
            Thread.sleep(1500)
            val itemData = mutableListOf<ItemData>()
            for (i in 0 until pageSize) {
                itemData.add(ItemData("this is item $i"))
            }

            page++

            exampleData.update(ExampleData(itemData, true))
        }
    }

    fun loadNextPage() {
        if (isNoMore) return
        if (isLoadMoreError) return

        thread(start = true) {
            Thread.sleep(1500)
            val itemData = mutableListOf<ItemData>()
            for (i in 0 until pageSize) {
                itemData.add(ItemData("this is item ${(i + pageSize) * page}"))
            }

            page++

            when {
                page >= 5 -> {
                    isNoMore = true
                    exampleData.update(ExampleData(emptyList(), false, true))
                }
                page % 2 == 0 -> {
                    isLoadMoreError = true
                    exampleData.update(ExampleData(emptyList(), false, false, true))
                }
                else -> exampleData.update(ExampleData(itemData, false))
            }
        }
    }


    fun retry() {
        isLoadMoreError = false
        loadNextPage()
    }

    data class ExampleData(
            val list: List<ItemData>,
            val isRefresh: Boolean = true,
            val isNoMore: Boolean = false,
            val isLoadMoreError: Boolean = false
    )

    data class ItemData(
            val title: String
    )
}