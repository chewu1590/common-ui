package com.example.commonui.samples

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import cn.woochen.common_ui.navigation.TabBottomNavigation
import cn.woochen.common_ui.navigation.iterator.ListTabIterator
import com.example.commonui.R
import com.example.commonui.samples.fragment.BlankFragment
import com.example.commonui.widget.TabMainView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
/**
 *底部导航菜单演示类
 *@author woochen
 *@time 2019/4/10 13:27
 */
class BottomNavigationActivity : AppCompatActivity() {
    private val mTabNames = arrayOf("首页", "消息", "我的")
    private val mTabRes =
        arrayOf(R.drawable.select_tab_work_order, R.drawable.select_tab_work_order, R.drawable.select_tab_work_order)
    private val mTabIterator = ListTabIterator<TabMainView>()
    private val fragments = mutableListOf<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        initFragments()
        initBottomNav()
    }

    private fun initFragments() {
        for (i in 0 until 3) fragments.add(BlankFragment.newInstance(mTabNames[i]))
    }

    private fun initBottomNav() {
        for (index in mTabNames.indices) {
            val tabMainView = TabMainView.Builder(this)
                .name(mTabNames[index])
                .icon(mTabRes[index])
                .build<TabMainView>()
            mTabIterator.addItem(tabMainView)
        }
        tabBottom.addItem(mTabIterator)
        tabBottom.onItemClickListener = object : TabBottomNavigation.OnItemClickListener {
            override fun onItemClick(position: Int) {
                tabBottom.selectItem(position)
                switchItem(position)
            }
        }
    }

    private fun switchItem(position: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,fragments[position]).commit()
    }
}
