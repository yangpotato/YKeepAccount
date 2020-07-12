package com.potato.ykeepaccount.addaccount.popup

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.impl.AttachListPopupView
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.adapter.TypeAdapter
import com.potato.ykeepaccount.room.entity.TypeEntity

class TypePopup(context: Context, var typeList: MutableList<TypeEntity>, var listener : OnItemClickListener?) : AttachPopupView(context) {
    private var recyclerview : RecyclerView? = null

    override fun getImplLayoutId(): Int {
        return com.lxj.xpopup.R.layout._xpopup_attach_impl_list
    }

    override fun initPopupContent() {
        super.initPopupContent()
        recyclerview = findViewById(R.id.recyclerView)
        recyclerview?.layoutManager = LinearLayoutManager(context)
        val adapter = object : BaseQuickAdapter<TypeEntity, BaseViewHolder>(R.layout.item_type, typeList){
            override fun convert(holder: BaseViewHolder, item: TypeEntity) {
                holder.getView<TextView>(R.id.tv).text = item.name
            }
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            listener?.onItemClick(typeList[position], position)
        }
//        val adapter = TypeAdapter(R.layout.item_type, typeList)
//        adapter.setNewInstance(typeList)
        recyclerview?.adapter = adapter
    }

    interface OnItemClickListener{
        fun onItemClick(item : TypeEntity, position : Int)
    }
}