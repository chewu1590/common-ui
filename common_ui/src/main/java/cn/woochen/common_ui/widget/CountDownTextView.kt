package cn.woochen.common_ui.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import cn.woochen.common_ui.R
import cn.woochen.common_ui.utils.DefaultActivityLifecycleCallbacks

/**
 *倒计时
 *@author woochen
 *@time 2019/4/10 13:49
 */
class CountDownTextView : TextView {
    private var mActivity: Activity? = context as Activity
    private val mLifecycle = object : DefaultActivityLifecycleCallbacks() {

        override fun onActivityDestroyed(activity: Activity) {
            mTimer.cancel()
            mActivity?.application?.unregisterActivityLifecycleCallbacks(this)
            mActivity = null
        }
    }
    private var mTotalTime = 60 * 1000L
    private var mIntervalTime = 1 * 1000L
    private var mCountTag: String? = "秒"
    private var mEndTip: String? = "重新发送"
    private lateinit var mTimer: CountDownTimer
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
        init()
    }

    private fun init() {
        mActivity?.application?.registerActivityLifecycleCallbacks(mLifecycle)
        mTimer = object : CountDownTimer(mTotalTime, mIntervalTime) {
            override fun onFinish() {
                isEnabled = true
                if (mEndColor != Color.BLACK) setTextColor(mEndColor)
                text = mEndTip
            }

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
        mTimer.start()
    }
}