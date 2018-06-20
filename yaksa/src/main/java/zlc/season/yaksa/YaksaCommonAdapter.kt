package zlc.season.yaksa

open class YaksaCommonAdapter : YaksaBaseAdapter() {

    internal var headerList = mutableListOf<YaksaItem>()
    internal var itemList = mutableListOf<YaksaItem>()
    internal var footerList = mutableListOf<YaksaItem>()

    protected open fun items(): List<YaksaItem> {
        val result = mutableListOf<YaksaItem>()
        result.addAll(headerList)
        result.addAll(itemList)
        result.addAll(footerList)
        return result
    }

    override fun update() {
        submitList(items())
    }
}