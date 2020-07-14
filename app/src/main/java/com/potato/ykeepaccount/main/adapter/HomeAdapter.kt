package com.potato.ykeepaccount.main.adapter

import com.base.commom.utils.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.AccountResultEntity

class HomeAdapter(layoutResId: Int, data : MutableList<AccountResultEntity>) : BaseQuickAdapter<AccountResultEntity, BaseViewHolder>(
    R.layout.item_home, data
) {
    override fun convert(holder: BaseViewHolder, item: AccountResultEntity) {
        holder.setText(R.id.tv_category, "${item.categoryPrimaryName}-${item.categoryName}")
            .setText(R.id.tv_money, "${item.account.money}")
            .setText(R.id.tv_remark, item.account.remark)
        GlideUtil.show(context, item.account.imgUrl, holder.getView(R.id.iv_img))
    }
}