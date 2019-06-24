package zlc.season.yasha

import zlc.season.sange.SangeDataSource

open class YashaDataSource : SangeDataSource<YashaItem>() {
    override fun onStateChanged(newState: Int) {
        setState(YashaStateItem(newState, ::retry))
    }
}