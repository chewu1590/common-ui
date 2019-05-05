package com.example.commonui.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.woochen.common_ui.widget.NodeProgress
import cn.woochen.common_ui.widget.node_progress.LogisticsData
import cn.woochen.common_ui.widget.node_progress.NodeProgressAdapter
import kotlinx.android.synthetic.main.activity_node_progrees.*


class NodeProgreesActivity : AppCompatActivity() {

    val logisticsDatas = mutableListOf<LogisticsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.commonui.R.layout.activity_node_progrees)
        initView()
    }

    private fun initView() {
       /* logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊市中转部】,上一站是【】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【潍坊市中转部】装车,正发往【潍坊奎文代派】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊】,上一站是【潍坊市中转部】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【武汉分拨中心】装车,正发往【晋江分拨中心】"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"))
        logisticsDatas.add(LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"))
        nodeProgressView.setNodeProgressAdapter(object: NodeProgressAdapter {
            override fun getData(): MutableList<LogisticsData> = logisticsDatas

            override fun getCount(): Int = logisticsDatas.size
        })*/
        val nodes = mutableListOf<NodeProgress.NodeElement>(
            NodeProgress.NodeElement(true,"乌鲁木齐"),
            NodeProgress.NodeElement(false,"杭州市"),
            NodeProgress.NodeElement(true,"上区"),
            NodeProgress.NodeElement(false,"齐齐哈尔")
        )
        nodeProgress.setAdapter(nodes)
    }
}
