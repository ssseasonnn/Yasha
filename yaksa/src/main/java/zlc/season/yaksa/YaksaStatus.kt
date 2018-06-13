package zlc.season.yaksa

sealed class YaksaStatus

class Loading : YaksaStatus()

class Error : YaksaStatus()

class Empty : YaksaStatus()