package zlc.season.yaksa

open class YaksaCommonDsl(override val adapter: YaksaCommonAdapter) : YaksaBaseDsl(adapter) {

    /**
     * Render headers with given data.
     *
     * @param dataSource The data source that will be used to render the header.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderHeaders(dataSource: List<T>,
                          clear: Boolean = false,
                          updateImmediately: Boolean = true,
                          block: (T) -> YaksaItem) {
        adapter.headerList.clearIf(clear)
        dataSource.forEach {
            adapter.headerList.add(block(it))
        }
        adapter.updateIf(updateImmediately)
    }


    /**
     * Render headers by dsl with given data.
     *
     * @param dataSource The data source that will be used to render the header.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderHeadersByDsl(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: YaksaItemDsl.(T) -> Unit) {
        adapter.headerList.clearIf(clear)
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            adapter.headerList.add(dsl.internalItem())
        }
        adapter.updateIf(updateImmediately)
    }

    /**
     * Render items with given data.
     *
     * @param dataSource The data source that will be used to render the item.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderItems(dataSource: List<T>,
                        clear: Boolean = false,
                        updateImmediately: Boolean = true,
                        block: (T) -> YaksaItem) {
        adapter.itemList.clearIf(clear)
        dataSource.forEach {
            adapter.itemList.add(block(it))
        }
        adapter.updateIf(updateImmediately)
    }

    /**
     * Render items by dsl with given data.
     *
     * @param dataSource The data source that will be used to render the item.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderItemsByDsl(dataSource: List<T>,
                             clear: Boolean = false,
                             updateImmediately: Boolean = true,
                             block: YaksaItemDsl.(T) -> Unit) {
        adapter.itemList.clearIf(clear)
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            adapter.itemList.add(dsl.internalItem())
        }

        adapter.updateIf(updateImmediately)
    }

    /**
     * Render footers with given data.
     *
     * @param dataSource The data source that will be used to render the footer.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderFooters(dataSource: List<T>,
                          clear: Boolean = false,
                          updateImmediately: Boolean = true,
                          block: (T) -> YaksaItem) {
        adapter.footerList.clearIf(clear)
        dataSource.forEach {
            adapter.footerList.add(block(it))
        }

        adapter.updateIf(updateImmediately)
    }

    /**
     * Render footers by dsl with given data.
     *
     * @param dataSource The data source that will be used to render the footer.
     *
     * @param clear If true, the old data will be cleared. Default is false.
     *
     * @param updateImmediately  If true, the Adapter will be updated immediately. Default is true.
     *
     * @param block Render block.
     */
    fun <T> renderFootersByDsl(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: YaksaItemDsl.(T) -> Unit) {
        adapter.footerList.clearIf(clear)
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            adapter.footerList.add(dsl.internalItem())
        }

        adapter.updateIf(updateImmediately)
    }
}