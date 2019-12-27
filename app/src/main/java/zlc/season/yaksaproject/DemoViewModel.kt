package zlc.season.yaksaproject

import androidx.lifecycle.ViewModel

class DemoViewModel : ViewModel() {
    val dataSource = DemoDataSource()

    val refresh = dataSource.refresh


    fun refresh() {
        dataSource.invalidate()
    }
}