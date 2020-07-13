package zlc.season.yaksaproject

import androidx.lifecycle.MutableLiveData
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem

class DemoDataSource : YashaDataSource() {
    val refresh = MutableLiveData<Boolean>()
    var page = 0

    override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
        page = 0

        refresh.postValue(true)

        Thread.sleep(1500)

        val headers = mutableListOf<YashaItem>()
        headers.add(HeaderItem(-1, "A1", typeConflict = "AAA"))
        headers.add(HeaderItem(-2, "B1", typeConflict = "BBB"))
        for (i in 0 until 2) {
            headers.add(HeaderItem(i))
        }

        val items = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            items.add(NormalItem(i))
        }

        val footers = mutableListOf<YashaItem>()
        for (i in 0 until 2) {
            footers.add(FooterItem(i))
        }

        addHeaders(headers, delay = true)
        addFooters(footers, delay = true)

        loadCallback.setResult(items)

        refresh.postValue(false)
    }

    override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
        page++

        //Mock load failed.
        //模拟加载失败.
        if (page % 3 == 0) {
            loadCallback.setResult(null)
            return
        }

        Thread.sleep(1500)
        val items = mutableListOf<YashaItem>()
        for (i in page * 10 until (page + 1) * 10) {
            items.add(NormalItem(i))
        }

        loadCallback.setResult(items)
    }
}