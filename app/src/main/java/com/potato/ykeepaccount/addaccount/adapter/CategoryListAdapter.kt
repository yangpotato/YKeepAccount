package com.potato.ykeepaccount.addaccount.adapter

import com.base.commom.utils.GlideUtil
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity

class CategoryListAdapter() : BaseDelegateMultiAdapter<CategoryEntity, BaseViewHolder>(),
    LoadMoreModule {
    companion object{
        const val TEXT = 0
        const val ITEM = 1
    }
    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<CategoryEntity>() {
            override fun getItemType(data: List<CategoryEntity>, position: Int): Int {
                val item = data[position]
                return if(item.fatherId != 0L) ITEM else TEXT
            }
        })
        getMultiTypeDelegate()?.addItemType(TEXT, R.layout.item_category_list_parent)
            ?.addItemType(ITEM, R.layout.item_category_list)
    }
    override fun convert(holder: BaseViewHolder, item: CategoryEntity) {
        when(holder.itemViewType){
            TEXT-> {
                holder.setText(R.id.tv, item.categoryName)
            }
            ITEM-> {
                holder.setText(R.id.tv_category, item.categoryName)
                GlideUtil.show(context, item.categoryUrl, holder.getView(R.id.iv))
            }
        }

    }
}