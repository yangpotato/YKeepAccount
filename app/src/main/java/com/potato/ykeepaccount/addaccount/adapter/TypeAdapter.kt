package com.potato.ykeepaccount.addaccount.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.TypeEntity

class TypeAdapter(layoutResId: Int) : BaseQuickAdapter<TypeEntity, BaseViewHolder>(layoutResId) {
    override fun convert(holder: BaseViewHolder, item: TypeEntity) {
        holder.getView<TextView>(R.id.tv).text = item.name
    }
}