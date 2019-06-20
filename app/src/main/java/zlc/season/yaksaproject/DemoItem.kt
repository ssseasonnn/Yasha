package zlc.season.yaksaproject

import zlc.season.yasha.YashaItem

const val NORMAL = 0
const val HEADER = 1
const val FOOTER = 2
const val STATE = 3

class NormalItem(val i: Int) : YashaItem {
    override fun viewType() = NORMAL

    override fun toString() = "Item $i"
}

class HeaderItem(val i: Int) : YashaItem {
    override fun viewType() = HEADER

    override fun toString() = "Header $i"
}

class FooterItem(val i: Int) : YashaItem {
    override fun viewType() = FOOTER

    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : YashaItem {
    override fun viewType() = STATE
}