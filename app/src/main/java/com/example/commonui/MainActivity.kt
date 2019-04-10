package com.example.commonui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.commonui.adapter.MainAdapter
import com.example.commonui.samples.BottomNavigationActivity
import com.example.commonui.samples.CommonActivity
import com.example.commonui.samples.SmartRecycleViewActivity
import com.example.commonui.samples.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mItemNames = mutableListOf("Common","底部导航栏","SmartRecycleview","SplashView")
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
        }
    }


   private fun start(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)
    }


}
