package com.example.commonui.samples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.woochen.common_ui.widget.SplashView
import com.example.commonui.R
/**
 *闪屏页演示类
 *@author woochen
 *@time 2019/4/10 16:29
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initSplashView()
    }

    private fun initSplashView() {
        SplashView.showSplashView(this, 3, R.drawable.ic_ad, object : SplashView.OnSplashViewActionListener {
            override fun onSplashImageClick(actionUrl: String?) {
            }


            override fun onSplashViewDismiss(initiativeDismiss: Boolean) {
                Toast.makeText(this@SplashActivity,"进入主页",Toast.LENGTH_SHORT).show()
//                SplashView.updateSplashData(this@SplashActivity,"图片url","webview url")
            }
        })
    }
}
