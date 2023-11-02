package zlc.season.yasha.compose

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.update
import zlc.season.sange.datasource.DataSource
import zlc.season.sange.datasource.FetchState

open class ComposeDataSource(
    coroutineScope: CoroutineScope,
    invalidate: Boolean = true,
    private val enableDefaultState: Boolean = false
) : DataSource<ComposeItem>(coroutineScope) {
    private val loadAround: (Int) -> Unit = { dispatchLoadAround(it) }

    private val _dataFlow = MutableStateFlow<List<ComposeItem>>(emptyList())
    val dataFlow = _dataFlow.onSubscription {
        if (invalidate) {
            invalidate(false)
        }
    }

    override fun notifySubmitList(submitNow: Boolean) {
        _dataFlow.update {
            DataList(toList(), loadAround)
        }
    }

    override fun onFetchStateChanged(newState: FetchState) {
        if (enableDefaultState) {
            setState(ComposeStateItem(newState, ::retry))
        }
    }
}