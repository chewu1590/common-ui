package com.example.commonui.adapter

import android.content.Context
import cn.woochen.common_ui.adapter.CommonRecyclerAdapter
import cn.woochen.common_ui.adapter.MultiTypeSupport
import com.example.commonui.R

class SmartAdapter: CommonRecyclerAdapter<String> {

    constructor(context: Context,datas:List<String>):super(context,datas, R.layout.item_main)

    constructor(context: Context, datas:List<String>,multiTypeSupport: MultiTypeSupport<String>):super(context,datas,multiTypeSupport)

    override fun convert(holder: ViewHolder, item: String) {
        mContext
        holder.setText(R.id.tv_text,item)
    }

}