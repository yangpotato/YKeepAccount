package com.potato.ykeepaccount.addaccount.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity

class CategoryListAdapter(list : MutableList<CategoryEntity>) : BaseQuickAdapter<CategoryEntity, BaseViewHolder>(R.layout.item_category_list, list),
    LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CategoryEntity) {
//        holder.setIsRecyclable(false)
        holder.setText(R.id.tv_category, item.categoryName)
    }
}