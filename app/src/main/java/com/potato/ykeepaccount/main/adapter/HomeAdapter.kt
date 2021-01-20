package com.potato.ykeepaccount.main.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.room.entity.AccountResultEntity

class HomeAdapter(list: MutableList<AccountResultEntity>) :
	BaseQuickAdapter<AccountResultEntity, BaseViewHolder>(R.layout.item_home, list) {

	override fun convert(holder: BaseViewHolder, item: AccountResultEntity) {
		holder.setText(R.id.tv_content, "${item.categoryPrimaryName} - ${item.categoryName}")
			.setText(R.id.tv_money, item.account.money.toString())
			.setTextColor(
				R.id.tv_money,
				ContextCompat.getColor(context, if (item.coefficient == 1) R.color.colorPrimary else R.color.black)
			)
		if (holder.layoutPosition > 0 && item.account.getCostTimeFormat() == data[holder.layoutPosition - 1].account.getCostTimeFormat()) {
			//和上一条为同一天，隐藏时间tab
			holder.setGone(R.id.cl_tab, true)
		} else {
			holder.setGone(R.id.cl_tab, false)
				.setText(R.id.tv_time, item.account.getCostTimeFormat())
				.setText(R.id.tv_expenses, "支出 ${item.expensesMoney}")
				.setText(R.id.tv_income, "收入 ${item.incomeDayMoney}")
		}
	}
}