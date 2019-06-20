package zlc.season.yaksaproject

import android.arch.lifecycle.ViewModel

class DemoViewModel : ViewModel() {
    val dataSource = DemoDataSource()

    val refresh = dataSource.refresh


    fun refresh() {
        dataSource.invalidate()
    }
}