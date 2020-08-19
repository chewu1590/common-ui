package com.example.commonui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import cn.woochen.common_ui.dialog.AlertDialog
import cn.woochen.common_ui.widget.preview.PreviewDialogFragment
import com.example.commonui.adapter.MainAdapter
import com.example.commonui.samples.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mItemNames = mutableListOf("Common", "底部导航栏", "SmartRecycleview", "SplashView", "多图预览", "自定义弹窗")
    private val mMainAdapter by lazy {
        MainAdapter(this, mItemNames)
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
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this,
            androidx.recyclerview.widget.RecyclerView.VERTICAL, false)
        rv.adapter = mMainAdapter
        mMainAdapter.itemClickListener = object : MainAdapter.OnItemClickListener {
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
                images.add("http://img1.imgtn.bdimg.com/it/u=2735633715,2749454924&fm=26&gp=0.jpg")
                PreviewDialogFragment.newInstance(0, images).show(this@MainActivity)
            }
            5 -> {
                confirmAndCancel(this, "哈哈哈哈")
            }
        }
    }


    private fun start(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }


    fun confirmAndCancel(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setContentView(R.layout.dialog_common)
            .setText(R.id.tv_message, message)
            .setOnClickListener(R.id.tv_cancel, View.OnClickListener { builder.dialog.dismiss() })
            .setOnClickListener(R.id.tv_confirm, View.OnClickListener {
                builder.dialog.dismiss()
            })
            .show()
    }


}
