package zlc.season.yasha

class YashaStateItem(val state: Int, val retry: () -> Unit) : YashaItem