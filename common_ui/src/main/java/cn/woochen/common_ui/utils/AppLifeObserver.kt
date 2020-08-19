package cn.woochen.common_ui.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *进程前后台监听
 *@author woochen
 *@time 2020/8/19 11:22 AM
 */
class AppLifeObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        //应用进入前台
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        //应用进入后台
    }
}