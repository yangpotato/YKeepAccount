package com.potato.ykeepaccount.addaccount.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R

class CategoryListAdapter(list : MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_category_list, list),
    LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: String) {
//        holder.setIsRecyclable(false)
    }
}