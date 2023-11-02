package zlc.season.yasha

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import zlc.season.sange.SangeDataSource
import zlc.season.sange.datasource.FetchState

open class YashaDataSource(
    coroutineScope: CoroutineScope = MainScope(),
    private val enableDefaultState: Boolean = false,
) : SangeDataSource<YashaItem>(coroutineScope) {

    override fun shouldLoadAfter(position: Int): Boolean {
        return position == headerSize() + itemSize() - 1
    }

    override fun onFetchStateChanged(newState: FetchState) {
        if (enableDefaultState) {
            setState(YashaStateItem(newState, ::retry))
        }
    }
}