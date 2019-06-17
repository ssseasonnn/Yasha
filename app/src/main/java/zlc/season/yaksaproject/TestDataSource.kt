package zlc.season.yaksaproject

import zlc.season.paging.MultiDataSource
import zlc.season.yasha.YaksaItem

class TestDataSource : MultiDataSource<YaksaItem>() {

    var page = 0

    override fun loadInitial(loadCallback: LoadCallback<YaksaItem>) {
        page = 0

        Thread.sleep(1000)
        val list = mutableListOf<YaksaItem>()
        for (i in 0 until 10) {
            list.add(ExampleViewModel.ItemData("item $i"))
        }
        loadCallback.setResult(list)
    }

    override fun loadAfter(loadCallback: LoadCallback<YaksaItem>) {
        page++

        Thread.sleep(1000)
        val list = mutableListOf<YaksaItem>()
        for (i in page * 10 until (page + 1) * 10) {
            list.add(ExampleViewModel.ItemData("item $i"))
        }
        loadCallback.setResult(list)
    }
}