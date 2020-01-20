package com.example.commonui.samples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import cn.woochen.common_ui.adapter.MultiTypeSupport
import cn.woochen.common_ui.adapter.OnItemClickListener
import com.example.commonui.R
import com.example.commonui.adapter.SmartAdapter
import com.example.commonui.util.logcom
import kotlinx.android.synthetic.main.activity_smart_recycle_view.*

/**
 *RecycleView演示类
 *@author woochen
 *@time 2019/4/10 13:27
 */
class SmartRecycleViewActivity : AppCompatActivity() {
    private val datas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_recycle_view)
        initRecycleView()
    }

    private fun initRecycleView() {
        refreshLayout.setOnRefreshListener {
            it.finishRefresh(2000)
        }
        refreshLayout.setOnLoadMoreListener {
            it.finishLoadMore(2000)
        }
        for (i in 0 until 50) datas.add("$i")
        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //单条目
//        val smartAdapter = SmartAdapter(this, datas)
        //多条目
        val smartAdapter = SmartAdapter(this, datas, object : MultiTypeSupport<String> {
            override fun getLayoutId(item: String, position: Int): Int {
                var layoutResId: Int
                layoutResId =
                    (if (item.toInt() % 2 == 0) R.layout.item_main else R.layout.item_main2)
                return layoutResId
            }
        })
        rv_list.adapter = smartAdapter
        smartAdapter.mItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                logcom("点击：${System.currentTimeMillis()}")
                Toast.makeText(this@SmartRecycleViewActivity, "$position", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
