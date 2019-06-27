package zlc.season.yaksaproject

import zlc.season.yasha.YashaItem

class NormalItem(val i: Int) : YashaItem {
    override fun toString() = "Item $i"
}

class HeaderItem(val i: Int) : YashaItem {
    override fun toString() = "Header $i"
}

class FooterItem(val i: Int) : YashaItem {
    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : YashaItem