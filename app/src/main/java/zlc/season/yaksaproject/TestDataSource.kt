package zlc.season.yaksaproject

import zlc.season.paging.MultiDataSource
import zlc.season.yasha.YashaItem

class TestDataSource : MultiDataSource<YashaItem>() {

    var page = 0

    override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
        page = 0

        val header = HeaderData("this is header")
        addHeader(header, delay = true)

        Thread.sleep(1000)
        val list = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            list.add(ItemData(i, "item $i"))
        }
        loadCallback.setResult(list)
    }

    override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
        page++

        Thread.sleep(1000)
        val list = mutableListOf<YashaItem>()
        for (i in page * 10 until (page + 1) * 10) {
            list.add(ItemData(i, "item $i"))
        }
        loadCallback.setResult(list)
    }
}