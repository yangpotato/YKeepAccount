package com.potato.ykeepaccount.main.adapter

import androidx.core.content.ContextCompat
import com.base.commom.utils.GlideUtil
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.AccountResultEntity

class HomeAdapter(data : MutableList<AccountResultEntity>) : BaseDelegateMultiAdapter<AccountResultEntity, BaseViewHolder>(
    data
) {
    companion object{
        const val INCOME = 0;
        const val SPEND = 1;
    }
    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<AccountResultEntity>(){
            override fun getItemType(data: List<AccountResultEntity>, position: Int): Int {
                val item = data[position]
                if(item.coefficient == 1)
                    return INCOME
                return SPEND
            }
        })
        getMultiTypeDelegate()
            ?.addItemType(INCOME, R.layout.item_home_reverse)
            ?.addItemType(SPEND, R.layout.item_home)
    }

    override fun convert(holder: BaseViewHolder, item: AccountResultEntity) {
        when(holder.itemViewType){
            INCOME -> {
                holder.setText(R.id.tv_category, "${item.typePrimaryName}-${item.typeName}")
                holder.setTextColor(
                    R.id.tv_money,
                    ContextCompat.getColor(context, R.color.income_color)
                )
                GlideUtil.show(context, R.mipmap.icon_income, holder.getView(R.id.iv_category))
            }
            SPEND -> {
                holder.setText(R.id.tv_category, "${item.categoryPrimaryName}-${item.categoryName}")
                holder.setTextColor(
                    R.id.tv_money,
                    ContextCompat.getColor(context, R.color.spend_color)
                )
                GlideUtil.show(context, item.categoryUrl, holder.getView(R.id.iv_category))
            }
        }
        holder.setText(R.id.tv_money, "¥${item.account.money}")
            .setText(R.id.tv_remark, item.account.remark)
        GlideUtil.show(context, item.account.imgUrl, holder.getView(R.id.iv_img))

    }
}