package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager

class YaksaDsl(val adapter: YaksaAdapter) {
    internal var orientation = VERTICAL
    internal var reverse = false
    internal var spanCount = 1

    /**
     * Set the orientation, default is [android.support.v7.widget.RecyclerView.VERTICAL]
     *
     * @param orientation   Layout orientation. Should be [android.support.v7.widget.RecyclerView.VERTICAL]
     *                      or [android.support.v7.widget.RecyclerView.HORIZONTAL]
     */
    fun orientation(orientation: Int) {
        this.orientation = orientation
    }

    /**
     * Set whether to reverse the list
     *
     * @param reverse When set to true, layouts from end to start.
     */
    fun reverse(reverse: Boolean) {
        this.reverse = reverse
    }

    /**
     * Set SpanCount for Grid and Stagger
     *
     * @param spanCount spanCount
     */
    fun spanCount(spanCount: Int) {
        this.spanCount = spanCount
    }

    /**
     * Render a state item, such as error, empty, or loading indicator
     */
    fun <T> renderStateItem(t: T, block: (T) -> YaksaState) {
        if (adapter is YaksaCommonAdapter) {
            adapter.setState(block(t))
        }
    }

    /**
     * Render a state item, such as error, empty, or loading indicator
     */
    fun <T> renderStateItemByDsl(t: T, block: YaksaStateDsl.(T) -> Unit) {
        val dsl = YaksaStateDsl()
        dsl.block(t)
        if (adapter is YaksaCommonAdapter) {
            adapter.setState(dsl.internalState())
        }
    }


    /**
     * Render placeholder item with given data
     */
    fun <T> renderPlaceholders(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: (T) -> YaksaItem) {
        dataSource.forEach {
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.placeholderList, block(it))
            }
        }

        update(updateImmediately)
    }

    fun <T> renderPlaceholdersByDsl(dataSource: List<T>,
                                    clear: Boolean = false,
                                    updateImmediately: Boolean = true,
                                    block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.placeholderList, dsl.internalItem())
            }
        }

        update(updateImmediately)
    }

    /**
     * Render headers with given data
     */
    fun <T> renderHeaders(dataSource: List<T>,
                          clear: Boolean = false,
                          updateImmediately: Boolean = true,
                          block: (T) -> YaksaItem) {
        dataSource.forEach {
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.headerList, block(it))
            }
        }

        update(updateImmediately)
    }


    fun <T> renderHeadersByDsl(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.headerList, dsl.internalItem())
            }
        }

        update(updateImmediately)
    }

    /**
     * Render items with given data
     */
    fun <T> renderItems(dataSource: List<T>,
                        clear: Boolean = false,
                        updateImmediately: Boolean = true,
                        block: (T) -> YaksaItem) {
        dataSource.forEach {
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.itemList, block(it))
            }
        }

        update(updateImmediately)
    }


    fun <T> renderItemsByDsl(dataSource: List<T>,
                             clear: Boolean = false,
                             updateImmediately: Boolean = true,
                             block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.itemList, dsl.internalItem())
            }
        }

        update(updateImmediately)
    }

    /**
     * Render footers with given data
     */
    fun <T> renderFooters(dataSource: List<T>,
                          clear: Boolean = false,
                          updateImmediately: Boolean = true,
                          block: (T) -> YaksaItem) {
        dataSource.forEach {
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.footerList, block(it))
            }
        }

        update(updateImmediately)
    }

    fun <T> renderFootersByDsl(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.footerList, dsl.internalItem())
            }
        }

        update(updateImmediately)
    }

    /**
     * Render extras with given data
     */
    fun <T> renderExtras(dataSource: List<T>,
                         clear: Boolean = false,
                         updateImmediately: Boolean = true,
                         block: (T) -> YaksaItem) {
        dataSource.forEach {
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.extraList, block(it))
            }
        }
        update(updateImmediately)
    }

    fun <T> renderExtrasByDsl(dataSource: List<T>,
                              clear: Boolean = false,
                              updateImmediately: Boolean = true,
                              block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            if (adapter is YaksaCommonAdapter) {
                addItem(clear, adapter.extraList, dsl.internalItem())
            }
        }
        update(updateImmediately)
    }

    private fun addItem(clear: Boolean, list: MutableList<YaksaItem>, item: YaksaItem) {
        if (clear) {
            list.clear()
        }
        list.add(item)
    }

    private fun update(updateImmediately: Boolean) {
        if (updateImmediately) {
            adapter.update()
        }
    }


    internal fun checkStagger(source: StaggeredGridLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.spanCount == spanCount) {
            return false
        }
        return true
    }

    internal fun checkLinear(source: LinearLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.reverseLayout == reverse) {
            return false
        }
        return true
    }

    internal fun checkGrid(source: GridLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.spanCount == spanCount &&
                source.reverseLayout == reverse) {
            return false
        }
        return true
    }
}