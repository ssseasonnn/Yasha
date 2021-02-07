package zlc.season.yasha

import zlc.season.sange.SangeDataSource

open class YashaDataSource : SangeDataSource<YashaItem>() {

    override fun shouldLoadNext(position: Int): Boolean {
        return position == itemSize() - 1
    }

    override fun onStateChanged(newState: Int) {
        setState(YashaStateItem(newState, ::retry))
    }
}