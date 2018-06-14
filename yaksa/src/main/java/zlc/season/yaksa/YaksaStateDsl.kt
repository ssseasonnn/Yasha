package zlc.season.yaksa

class YaksaStateDsl : YaksaItemDsl() {
    private var isShowStateItem = true

    /**
     * Returns true the state item is displayed, false it is not displayed
     */
    fun isShowStateItem(isShowStateItem: Boolean) {
        this.isShowStateItem = isShowStateItem
    }

    fun internalState(): YaksaState {
        return object : YaksaState() {
            override fun renderStateItem(): YaksaItem {
                return internalItem()
            }

            override fun isShowStateItem(): Boolean {
                return isShowStateItem
            }
        }
    }
}