package zlc.season.yaksa

open class YaksaCommonStateDsl(override val adapter: YaksaCommonStateAdapter) : YaksaCommonDsl(adapter) {

    /**
     * Render a state item, such as error, empty, or loading indicator
     */
    fun <T> renderStateItem(t: T, block: (T) -> YaksaState) {
        adapter.setState(block(t))
    }

    /**
     * Render a state item, such as error, empty, or loading indicator
     */
    fun <T> renderStateItemByDsl(t: T, block: YaksaStateDsl.(T) -> Unit) {
        val dsl = YaksaStateDsl()
        dsl.block(t)
        adapter.setState(dsl.internalState())
    }
}