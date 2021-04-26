package zlc.season.yaksaproject

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class DemoViewModel(enableDefaultState: Boolean) : ViewModel(), CoroutineScope by MainScope() {
    val dataSource = DemoDataSource(this, enableDefaultState)

    val refresh = dataSource.refresh


    fun refresh() {
        dataSource.invalidate()
    }

    fun changeHeader() {
        dataSource.setHeader(0, HeaderItem(-1, "AA1", "AAA"))
        dataSource.setHeader(1, HeaderItem(-2, "BB1", "BBB"))
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}