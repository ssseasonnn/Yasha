package zlc.season.yasha

fun MutableList<*>.clearIf(clear: Boolean) {
    if (clear) this.clear()
}