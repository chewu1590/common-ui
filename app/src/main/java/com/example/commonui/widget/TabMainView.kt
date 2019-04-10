package com.example.commonui.widget

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import cn.woochen.common_ui.navigation.TabItemView
import com.example.commonui.R

/**
 * Created by Administrator on 2018/11/15.
 */
class TabMainView(build: Builder) : TabItemView<TabMainView.Builder>(build) {
    override fun setSelected(selected: Boolean) {
        getView<ImageView>(R.id.iv_tab_icon).isSelected = selected
        getView<TextView>(R.id.tv_tab_name).isSelected = selected
    }

    override fun initView() {
        setText(R.id.tv_tab_name, build.name)
        setImageRes(R.id.iv_tab_icon, build.iconRes)
    }

    class Builder : TabItemView.Builder {
        lateinit var name: String
        var iconRes: Int = 0
        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun icon(iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        override fun <T : TabItemView<*>> build(): T = TabMainView(this) as T
        constructor(context: Context) : super(context,R.layout.main_tab)
        constructor(context: Context, layoutId: Int) : super(context, layoutId)

    }
}