package zlc.season.yasha

abstract class YaksaState {
    /**
     * Return a state YaksaItem to display
     */
    abstract fun renderStateItem(): YaksaItem

    /**
     * Whether the state item is displayed
     */
    abstract fun isShowStateItem(): Boolean
}
