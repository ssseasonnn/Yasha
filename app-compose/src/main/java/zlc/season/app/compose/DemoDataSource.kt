package zlc.season.app.compose

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import zlc.season.yasha.compose.ComposeDataSource
import zlc.season.yasha.compose.ComposeItem

class DemoDataSource(coroutineScope: CoroutineScope) : ComposeDataSource(coroutineScope, enableDefaultState = true) {
    val isRefreshing = MutableStateFlow(false)
    var page = 0

    init {
        invalidate(true)
    }

    override suspend fun loadInitial(): List<ComposeItem>? {
        println("load init")
        page = 0

        isRefreshing.value = true

        delay(1500)

        val headers = mutableListOf<ComposeItem>()
        headers.add(HeaderItem(-1, "A1", typeConflict = "AAA"))
        headers.add(HeaderItem(-2, "B1", typeConflict = "BBB"))
        for (i in 0 until 2) {
            headers.add(HeaderItem(i))
        }

        val items = mutableListOf<ComposeItem>()
        for (i in 0 until 2) {
            items.add(NormalItem(i))
        }

        val footers = mutableListOf<ComposeItem>()
        for (i in 0 until 2) {
            footers.add(FooterItem(i))
        }

        addHeaders(headers, delay = true)
        addFooters(footers, delay = true)

        isRefreshing.value = false

        return items
    }

    override suspend fun loadAfter(): List<ComposeItem>? {
        println("load after")
        page++

        //Mock load failed.
        //模拟加载失败.
        if (page % 5 == 0) {
            return null
        }

        delay(1500)
        val items = mutableListOf<ComposeItem>()
        for (i in page * 2 until (page + 1) * 2) {
            items.add(NormalItem(i))
        }

        return items
    }
}