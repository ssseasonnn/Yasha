package zlc.season.yasha

import zlc.season.sange.SangeDataSource

open class YashaDataSource(private val enableDefaultState: Boolean = false) : SangeDataSource<YashaItem>() {

    override fun shouldLoadNext(position: Int): Boolean {
        return position == headerSize() + itemSize() - 1
    }

    override fun onStateChanged(newState: Int) {
        if (enableDefaultState) {
            setState(YashaStateItem(newState, ::retry))
        }
    }
}