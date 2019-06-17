package zlc.season.yasha

/**
 * Custom placeholder adapter.
 */
class YaksaPlaceholderAdapter : YaksaCommonStateAdapter() {
    internal var placeholderList = mutableListOf<YaksaItem>()

    fun updatePlaceholderIf(updateImmediately: Boolean) {
        if (updateImmediately) {
            submitList(placeholderList)
        }
    }
}