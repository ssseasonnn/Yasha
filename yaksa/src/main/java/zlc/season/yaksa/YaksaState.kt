package zlc.season.yaksa

abstract class YaksaState {
    abstract fun renderStateItem(): YaksaItem

    abstract fun isShowStateItem(): Boolean
}
