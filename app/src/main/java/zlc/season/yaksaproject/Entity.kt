package zlc.season.yaksaproject

import zlc.season.yasha.YashaItem

data class ItemData(
        val index: Int,
        val title: String
) : YashaItem

data class HeaderData(
        val header: String
) : YashaItem

data class FooterData(
        val footer: String
) : YashaItem