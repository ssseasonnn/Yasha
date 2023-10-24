package zlc.season.app.compose

import zlc.season.yasha.compose.ComposeItem

class NormalItem(val i: Int, val text: String = "") : ComposeItem {
    override fun toString() = "Item $i $text"
}

class HeaderItem(val i: Int, val text: String = "", var typeConflict: String? = null) : ComposeItem {
    override fun toString() = "Header $i $text"
}

class FooterItem(val i: Int) : ComposeItem {
    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : ComposeItem