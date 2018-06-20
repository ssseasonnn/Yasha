package zlc.season.yaksa

/**
 * Custom placeholder dsl.
 */
class YaksaPlaceholderDsl(override val adapter: YaksaPlaceholderAdapter) : YaksaCommonStateDsl(adapter) {
    /**
     * Render placeholders with given data.
     *
     * @param dataSource The data source that will be used to render the placeholder.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderPlaceholders(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: (T) -> YaksaItem) {
        adapter.placeholderList.clearIf(clear)
        dataSource.forEach {
            adapter.placeholderList.add(block(it))
        }
        adapter.updatePlaceholderIf(updateImmediately)
    }


    /**
     * Render placeholders by dsl with given data.
     *
     * @param dataSource The data source that will be used to render the placeholder.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderPlaceholdersByDsl(dataSource: List<T>,
                                    clear: Boolean = false,
                                    updateImmediately: Boolean = true,
                                    block: YaksaItemDsl.(T) -> Unit) {
        adapter.placeholderList.clearIf(clear)
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            adapter.placeholderList.add(dsl.internalItem())
        }
        adapter.updatePlaceholderIf(updateImmediately)
    }
}