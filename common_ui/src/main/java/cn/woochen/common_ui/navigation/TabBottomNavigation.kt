package cn.woochen.common_ui.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import cn.woochen.common_ui.navigation.iterator.ITabIterator
import java.lang.IllegalArgumentException
import java.util.ArrayList


/**
 * 底部导航栏菜单
 *
 * @author woochen123
 * @time 2017/11/23 10:23
 * @desc
 */
class TabBottomNavigation : LinearLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    private var tabViewList: MutableList<TabItemView<*>>? = null
    private var mCurrentIndex = -1

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 添加条目
     * @param tabIterator
     */
    fun addItem(tabIterator: ITabIterator) {
        if (tabViewList == null) {
            tabViewList = ArrayList()
        }
        var index = 0
        while (tabIterator.hasNext()) {
            val tabItem = tabIterator.next()
            val tabView = tabItem.tabView
            tabViewList!!.add(tabItem)
            //添加到当前布局
            addView(tabView)
            //重新设置布局
            val layoutParams = tabView!!.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1f
            layoutParams.gravity = Gravity.CENTER
            tabView.layoutParams = layoutParams
            //设置点击事件
            setOnItemClick(tabView, index)
            index++
        }
        //第一个条目设置为选中

        selectItem(0)
    }

    /**
     * 选中指定条目
     *
     * @param index
     */
    fun selectItem(index: Int) {
        if (index < tabViewList!!.size) {
            val currentIndex = if (mCurrentIndex < 0) 0 else mCurrentIndex
            tabViewList!![currentIndex].setSelected(false)
            mCurrentIndex = index
            tabViewList!![index].setSelected(true)
        } else {
            throw IllegalArgumentException("index over the list size!")
        }
    }

    /**
     * 找到指定条目
     * @return
     */
    fun getItemView(position: Int): TabItemView<*> {
        return tabViewList!![position]
    }

    /**
     * 条目点击事件
     *
     * @param tabView
     * @param i
     */
    private fun setOnItemClick(tabView: View, i: Int) {
        tabView.setOnClickListener {
            if (onItemClickListener != null) {
                //把之前的设置为未选中，当前的设置为选中
//                selectItem(i)
                onItemClickListener!!.onItemClick(i)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
