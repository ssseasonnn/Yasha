package zlc.season.yaksaproject

import zlc.season.yasha.YaksaItem

data class ItemData(
        val index: Int,
        val title: String
) : YaksaItem

data class HeaderData(
        val header: String
) : YaksaItem

data class FooterData(
        val footer: String
) : YaksaItem