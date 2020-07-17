package com.potato.ykeepaccount.main.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.base.commom.utils.GlideUtil
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.AccountResultEntity

class HomeAdapter(data : MutableList<AccountResultEntity>) : BaseQuickAdapter<AccountResultEntity, BaseViewHolder>(
    R.layout.item_home, data
) {

    override fun convert(holder: BaseViewHolder, item: AccountResultEntity) {
        if(item.coefficient == 1) {
            holder.setText(R.id.tv_category, "${item.categoryPrimaryName}-${item.categoryName}")
            holder.setTextColor(
                R.id.tv_money,
                ContextCompat.getColor(context, R.color.spend_color)
            )
            GlideUtil.show(context, item.categoryUrl, holder.getView(R.id.iv_category))
            holder.getView<View>(R.id.iv_point).isSelected = true
        }else{
            holder.setText(R.id.tv_category, "${item.typePrimaryName}-${item.typeName}")
            holder.setTextColor(
                R.id.tv_money,
                ContextCompat.getColor(context, R.color.income_color)
            )
            holder.getView<View>(R.id.iv_point).isSelected = false
            GlideUtil.show(context, R.mipmap.icon_income, holder.getView(R.id.iv_category))
        }

        holder.setText(R.id.tv_money, "¥${item.account.money}")
            .setText(R.id.tv_remark, item.account.remark)
            .setText(R.id.tv_time, item.account.getCostTimeFormat())
    }
}