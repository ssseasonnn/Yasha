package zlc.season.yasha

import zlc.season.sange.MultiDataSource

open class YashaDataSource : MultiDataSource<YashaItem>() {
    override fun onStateChanged(newState: Int) {
        setState(YashaStateItem(newState, ::retry))
    }
}