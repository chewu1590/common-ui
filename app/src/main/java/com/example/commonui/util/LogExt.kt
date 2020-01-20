package com.example.commonui.util

import com.example.commonui.BuildConfig


/**
 * weex日志
 */
fun Any.logWeex(msg: String?) {
    if (BuildConfig.DEBUG && msg != null) println("weex_log -----------> $msg")
}

/**
 * 常规日志打印
 * 过滤关键字 logcom
 */
fun Any.logcom(msg: String?){
    if (BuildConfig.DEBUG && msg != null) println("logcom -----------> $msg")
}

/**
 * UI日志打印
 * 过滤关键字 logui
 */
fun Any.logui(msg: String?){
    if (BuildConfig.DEBUG && msg != null) println("logui -----------> $msg")
}

/**
 * 流程日志打印
 * 过滤关键字 logprocess
 */
fun Any.logprocess(msg: String?){
    if (BuildConfig.DEBUG && msg != null) println("logprocess -----------> $msg")
}


