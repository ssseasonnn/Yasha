package zlc.season.yasha

private const val HASH_MULTIPLIER = 31

fun hashCode(value: Int, accumulator: Int): Int {
    return accumulator * HASH_MULTIPLIER + value
}

fun YashaItem.type(): Int {
    return if (typeConflict() != null) {
        hashCode(this::class.hashCode(), typeConflict().hashCode())
    } else {
        this::class.hashCode()
    }
}

inline fun <reified T : YashaItem> type(typeConflict: String? = null): Int {
    return if (typeConflict != null) {
        hashCode(T::class.hashCode(), typeConflict.hashCode())
    } else {
        T::class.hashCode()
    }
}