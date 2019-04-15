package com.example.commonui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.woochen.common_ui.widget.preview.PreviewDialogFragment
import com.example.commonui.adapter.MainAdapter
import com.example.commonui.samples.BottomNavigationActivity
import com.example.commonui.samples.CommonActivity
import com.example.commonui.samples.SmartRecycleViewActivity
import com.example.commonui.samples.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mItemNames = mutableListOf("Common","底部导航栏","SmartRecycleview","SplashView","多图预览")
    private val mMainAdapter by lazy {
        MainAdapter(this,mItemNames)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rv.adapter = mMainAdapter
        mMainAdapter.itemClickListener = object: MainAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                itemClickEvent(position)
            }
        }

    }


    private fun itemClickEvent(position: Int) {
        when (position) {
            0 -> {
                start(CommonActivity::class.java)
            }
            1 -> {
                start(BottomNavigationActivity::class.java)
            }
            2 -> {
                start(SmartRecycleViewActivity::class.java)
            }
            3 -> {
                start(SplashActivity::class.java)
            }
            4 -> {
                val images = arrayListOf<String>()
                images.add("http://img4q.duitang.com/uploads/item/201303/15/20130315223944_EvRW3.thumb.700_0.jpeg")
                images.add("http://pic26.nipic.com/20121227/10193203_131357536000_2.jpg")
                images.add("http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg")
                PreviewDialogFragment.newInstance(0,images).show(this@MainActivity)
            }
        }
    }


   private fun start(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)
    }


}
