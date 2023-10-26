package zlc.season.yasha.compose

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import zlc.season.sange.datasource.DataSource
import zlc.season.sange.datasource.FetchState

open class LazyListDataSource(
    coroutineScope: CoroutineScope,
    invalidateWhenInit: Boolean = true,
    private val enableDefaultState: Boolean = false
) : DataSource<ComposeItem>(coroutineScope) {
    private val loadAround: (Int) -> Unit = { dispatchLoadAround(it) }

    val dataFlow = MutableStateFlow<List<ComposeItem>>(emptyList())

    init {
        if (invalidateWhenInit) {
            invalidate(false)
        }
    }

    override fun notifySubmitList(submitNow: Boolean) {
        dataFlow.update {
            DataList(toList(), loadAround)
        }
    }

    override fun onFetchStateChanged(newState: FetchState) {
        if (enableDefaultState) {
            setState(ComposeStateItem(newState, ::retry))
        }
    }
}