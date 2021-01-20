package com.potato.ykeepaccount.main.presenter

import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.room.BaseRoomPresenter
import com.potato.ykeepaccount.room.RoomObserver
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import java.math.BigDecimal

class MainPresenter : BaseRoomPresenter<IMainContract.View>(), IMainContract.Presenter {
	override fun getAccountListByDateRange(startDate: Long, endDateLong: Long) {
		addSubscribe(
			createAccountDao().getAccount(),
			object : RoomObserver<MutableList<AccountResultEntity>>(mView) {
				override fun onSuccess(data: MutableList<AccountResultEntity>) {
                    var differentPosition = 0
					//收入
                    var income = BigDecimal("0")
					//支出
                    var expenses = BigDecimal("0")
                    for((position, item) in data.withIndex()){
                        if(position > 0 && item.account.getCostTimeFormat() == data[position- 1].account.getCostTimeFormat()){
							if(data[position - 1].coefficient == 1)
								income = income.add((data[position - 1].account.money))
							else
								expenses = expenses.add((data[position - 1].account.money))
                            LogUtil.i("和上一条记录是同一天，累加:->收入$income，支出$expenses")
                        }else{
							if(position > 0) {
								if (data[position - 1].coefficient == 1)
									income = income.add((data[position - 1].account.money))
								else
									expenses = expenses.add((data[position - 1].account.money))
							}
                            LogUtil.i("和上一条记录不是同一天，累加:->收入$income，支出$expenses，赋值:$differentPosition")
                            data[differentPosition].totalDayMoney = income.subtract(expenses)
                            data[differentPosition].incomeDayMoney = income
                            data[differentPosition].expensesMoney = expenses
							income = BigDecimal("0")
							expenses = BigDecimal("0")
                            differentPosition = position
                        }
                        if(position == data.size - 1){
							//如果是最后一条的话，则加上自身的金额
                            if(position > 0) {
								if (data[position].coefficient == 1)
									income = income.add((data[position].account.money))
								else
									expenses = expenses.add((data[position].account.money))
							}
                            LogUtil.i("当前是最后一条，累加:->收入$income，支出$expenses，赋值:$differentPosition")
							data[differentPosition].totalDayMoney = income.subtract(expenses)
							data[differentPosition].incomeDayMoney = income
							data[differentPosition].expensesMoney = expenses
							income = BigDecimal("0")
							expenses = BigDecimal("0")
                            differentPosition = position
                        }
                    }
					mView.showAccountList(data)
				}
			})
	}
}