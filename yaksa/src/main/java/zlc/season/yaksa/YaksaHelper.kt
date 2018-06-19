package zlc.season.yaksa

fun MutableList<*>.clearIf(clear: Boolean) {
    if (clear) this.clear()
}