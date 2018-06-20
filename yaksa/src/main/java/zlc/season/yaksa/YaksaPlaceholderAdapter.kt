package zlc.season.yaksa

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