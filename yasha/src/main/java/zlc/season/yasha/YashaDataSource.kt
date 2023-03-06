package zlc.season.yasha

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import zlc.season.sange.SangeDataSource

open class YashaDataSource(
    coroutineScope: CoroutineScope = MainScope(),
    private val enableDefaultState: Boolean = false,
) : SangeDataSource<YashaItem>(coroutineScope) {

    override fun shouldLoadNext(position: Int): Boolean {
        return position == headerSize() + itemSize() - 1
    }

    override fun onStateChanged(newState: Int) {
        if (enableDefaultState) {
            setState(YashaStateItem(newState, ::retry))
        }
    }
}