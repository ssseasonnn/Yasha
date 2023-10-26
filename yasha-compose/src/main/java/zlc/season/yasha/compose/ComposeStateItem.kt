package zlc.season.yasha.compose

import zlc.season.sange.datasource.FetchState

class ComposeStateItem(val state: FetchState, val retry: () -> Unit) : ComposeItem
