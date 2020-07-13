package zlc.season.yaksaproject

import androidx.lifecycle.ViewModel

class DemoViewModel : ViewModel() {
    val dataSource = DemoDataSource()

    val refresh = dataSource.refresh


    fun refresh() {
        dataSource.invalidate()
    }

    fun changeHeader() {
        dataSource.setHeader(0, HeaderItem(-1, "AA1", "AAA"))
        dataSource.setHeader(1, HeaderItem(-2, "BB1", "BBB"))
    }
}