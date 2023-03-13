package zlc.season.yasha.banner

import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem

class BannerDataSource : YashaDataSource() {
    var bannerItemCount = 0

    fun setData(data: List<YashaItem>) {
        this.clearAll(true)
        this.addItems(data)
        bannerItemCount = data.size
    }

    fun getInitPosition(): Int {
        return Int.MAX_VALUE / 2 / bannerItemCount * bannerItemCount
    }

    override fun getItemForAdapter(position: Int): YashaItem {
        return super.getItemForAdapter(position % bannerItemCount)
    }

    override fun getSizeForAdapter(): Int {
        return Int.MAX_VALUE
    }
}