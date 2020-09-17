package cn.woochen.common_ui

import android.content.Context
import androidx.startup.Initializer

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/9/16 5:12 PM
 * 修改备注：
 **/
class LibraryUI:Initializer<Unit> {

    override fun create(context: Context) {
        println("LibraryUI初始化测试")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}