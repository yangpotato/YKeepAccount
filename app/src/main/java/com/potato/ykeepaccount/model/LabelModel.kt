package com.potato.ykeepaccount.model

data class LabelModel(
    var id : Int,
    var name : String
) {
    companion object{
        const val TYPE = 0
        const val TIME = 1
    }
}