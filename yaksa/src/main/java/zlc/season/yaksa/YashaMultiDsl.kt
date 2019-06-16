package zlc.season.yaksa

import zlc.season.paging.PagingItem

class YashaMultiDsl(adapter: YaksaAdapter) : YaksaBaseDsl(adapter) {
    fun <T : PagingItem> renderHeader(item: T, block: (T) -> YashaViewHolder) {
        adapter.viewHolderMap[item.viewType()] = block
    }

    fun renderItemsByDsl(
            block: YashaItemDsl.() -> Unit) {
        val dsl = YashaItemDsl()
        dsl.block()
        adapter.viewHolderMap[dsl.resId] = dsl::internalItem
    }
}