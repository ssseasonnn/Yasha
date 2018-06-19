package zlc.season.yaksa

open class YaksaCommonDsl(override val adapter: YaksaCommonAdapter) : YaksaDsl(adapter) {
    /**
     * Render headers with given data
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
     * Render items with given data
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
     * Render footers with given data
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