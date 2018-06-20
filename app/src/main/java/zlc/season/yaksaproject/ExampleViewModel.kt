package zlc.season.yaksaproject

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.thread

class ExampleViewModel : ViewModel() {
    private val HEADER_DATA = mutableListOf<HeaderData>()
    private val ITEM_DATA = mutableListOf<ItemData>()
    private val FOOTER_DATA = mutableListOf<FooterData>()

    private val end = 200
    private val pageSize = 20
    private var start = 0

    private var isLoading = false

    val headerData: MutableLiveData<List<HeaderData>> = MutableLiveData()
    val itemData: MutableLiveData<ItemDataWrap> = MutableLiveData()
    val footerData: MutableLiveData<List<FooterData>> = MutableLiveData()

    val state: MutableLiveData<State> = MutableLiveData()

    init {
        for (i in 0 until end) {
            ITEM_DATA.add(ItemData("this is item $i"))

        }

        for (i in 0 until 2) {
            HEADER_DATA.add(HeaderData("Header $i"))
        }

        for (i in 0 until 2) {
            FOOTER_DATA.add(FooterData("Footer $i"))
        }
    }

    fun loadHeaderData(delayMills: Long = 0L) {
        thread(start = true) {
            Thread.sleep(if (delayMills == 0L) (Math.random() * 2000).toLong() else delayMills)
            headerData.update(HEADER_DATA)
        }
    }

    fun loadFooterData(delayMills: Long = 0L) {
        thread(start = true) {
            Thread.sleep(if (delayMills == 0L) (Math.random() * 2000).toLong() else delayMills)
            footerData.update(FOOTER_DATA)
        }
    }

    fun loadData(isRefresh: Boolean = false, delayMills: Long = 0L) {
        if (isRefresh) {
            start = 0
        }

        if (isLoading) {
            return
        }

        if (start == pageSize * 5) {
            state.update(State.Error())
            start += pageSize
            return
        }
        if (start >= pageSize * 8) {
            state.update(State.Empty())
            return
        }

        thread(start = true) {
            if (start >= end) return@thread

            isLoading = true

            Thread.sleep(if (delayMills == 0L) (Math.random() * 2000).toLong() else delayMills)

            state.update(State.Loading())

            itemData.update(ItemDataWrap(isRefresh, ITEM_DATA.slice(IntRange(start, start + pageSize - 1))))

            start += pageSize

            isLoading = false
        }
    }

    data class ItemDataWrap(
            val isRefresh: Boolean,
            val data: List<ItemData>
    )

    data class ItemData(
            val title: String
    )

    data class HeaderData(
            val header: String
    )

    data class FooterData(
            val footer: String
    )

    sealed class State {
        class Loading : State()
        class Error : State()
        class Empty : State()
    }
}