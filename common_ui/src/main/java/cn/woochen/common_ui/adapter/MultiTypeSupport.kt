package cn.woochen.common_ui.adapter

/**
 * 类描述：
 * 创建人：woochen123
 * 创建时间：2017/7/26 22:50
 */
interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    fun getLayoutId(item: T, position: Int): Int
}
