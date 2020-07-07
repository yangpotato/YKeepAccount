package com.potato.ykeepaccount.view.keyboard

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class KeyBoardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr) {
    private val mKeyList : MutableList<String>  = ArrayList()
    init {

    }
}