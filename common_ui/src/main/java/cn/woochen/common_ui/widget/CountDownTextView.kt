package cn.woochen.common_ui.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cn.woochen.common_ui.R

/**
 *倒计时
 *@author woochen
 *@time 2019/4/10 13:49
 */
class CountDownTextView : TextView, LifecycleObserver {
    private var mTotalTime = 60 * 1000L
    private var mIntervalTime = 1 * 1000L
    private var mCountTag: String? = "秒"
    private var mEndTip: String? = "重新发送"
    private  var mTimer: CountDownTimer? = null
    private var mCountColor: Int
    private var mEndColor: Int

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.CountDownTextView)
        mTotalTime = typedArray?.getInt(R.styleable.CountDownTextView_total_second, 60)!! * 1000L
        mIntervalTime = typedArray.getInt(R.styleable.CountDownTextView_interval_second, 1) * 1000L
        val countTag = typedArray.getString(R.styleable.CountDownTextView_count_tag)
        mCountTag = if (TextUtils.isEmpty(countTag)) mCountTag else countTag
        val endTip = typedArray.getString(R.styleable.CountDownTextView_end_tip)
        mEndTip = if (TextUtils.isEmpty(endTip)) mEndTip else endTip
        mCountColor = typedArray.getColor(R.styleable.CountDownTextView_count_color, Color.BLACK)
        mEndColor = typedArray.getColor(R.styleable.CountDownTextView_end_color, Color.BLACK)
        typedArray.recycle()
        init(context)
    }


    /**
     * 开启服务
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun pageDestroy() {
        mTimer?.cancel()
        mTimer = null
    }


    private fun init(context: Context?) {
        context?.let {
            if (it is ComponentActivity) {
                it.lifecycle.addObserver(this)
            }
        }
        mTimer = object : CountDownTimer(mTotalTime, mIntervalTime) {
            override fun onFinish() {
                isEnabled = true
                if (mEndColor != Color.BLACK) setTextColor(mEndColor)
                text = mEndTip
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (isEnabled) {
                    isEnabled = false
                    setTextColor(mCountColor)
                }
                text = "${millisUntilFinished / 1000L}$mCountTag"
            }
        }
    }

    fun start() {
        mTimer?.start()
    }
}