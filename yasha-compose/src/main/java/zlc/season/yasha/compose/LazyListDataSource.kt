package zlc.season.yasha.compose

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import zlc.season.sange.datasource.DataSource

open class LazyListDataSource<T : Any>(coroutineScope: CoroutineScope) : DataSource<T>(coroutineScope) {
    private val loadAround: (Int) -> Unit = { dispatchLoadAround(it) }

    val dataFlow = MutableStateFlow<List<T>>(emptyList())
    override fun notifySubmitList(submitNow: Boolean) {
        dataFlow.update {
            DataList(toList(), loadAround)
        }
    }
}