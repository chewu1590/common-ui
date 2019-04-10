package cn.woochen.common_ui.navigation.iterator


import cn.woochen.common_ui.navigation.TabItemView
import java.util.ArrayList


/**
 * 集合型迭代器
 * @author woochen123
 * @time 2017/11/22 15:00
 * @desc
 */

class ListTabIterator<in T : TabItemView<*>> :
    ITabIterator {
    private val mList: MutableList<T>
    internal var index = 0

    init {
        mList = ArrayList()
    }

    override fun next(): TabItemView<*> {
        return mList[index++]
    }

    override fun hasNext(): Boolean {
        return index < mList.size
    }

    fun addItem(item: T) {
        mList.add(item)
    }
}
