package cn.woochen.common_ui.navigation.iterator


import cn.woochen.common_ui.navigation.TabItemView

/**
 * Created by admin on 2017/11/22.
 */

interface ITabIterator {
    /**
     * 返回下一个元素
     */
    operator fun next(): TabItemView<*>

    /**
     * 是否有下一个元素
     * @return
     */
    operator fun hasNext(): Boolean
}
