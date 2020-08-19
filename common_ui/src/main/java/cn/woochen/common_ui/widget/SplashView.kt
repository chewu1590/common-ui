package cn.woochen.common_ui.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.woochen.common_ui.utils.DefaultActivityLifecycleCallbacks
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * 闪屏页面(需要在setContentView之后调用)
 */
class SplashView : FrameLayout {

    var splashImageView: ImageView? = null
    var skipButton: TextView? = null
    private var duration: Int = 6

    private var imgUrl: String? = null
    private var actUrl: String? = null

    private var isActionBarShowing = true

    private var mActivity: Activity? = null

    private var mOnSplashViewActionListener: OnSplashViewActionListener? = null

    private val mLifecycle = object : DefaultActivityLifecycleCallbacks() {

        override fun onActivityDestroyed(activity: Activity) {
            mHandler?.removeCallbacks(timerRunnable)
            mHandler = null
            mActivity?.application?.unregisterActivityLifecycleCallbacks(this)
            mActivity = null
        }
    }

    private var mHandler:Handler? = Handler()
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (0 == duration) {
                dismissSplashView(false)
                return
            } else setDuration(--duration)
            mHandler?.postDelayed(this, delayTime.toLong())
        }
    }

    private val splashSkipButtonBg = GradientDrawable()

    private fun setImage(image: Bitmap) {
        splashImageView?.setImageBitmap(image)
    }

    private fun setImage(resId: Int) {
        splashImageView?.setImageResource(resId)
    }

    constructor(context: Activity) : super(context) {
        mActivity = context
        initComponents()
    }

    constructor(context: Activity, attrs: AttributeSet) : super(context, attrs) {
        mActivity = context
        initComponents()
    }

    constructor(context: Activity, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mActivity = context
        initComponents()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Activity, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        mActivity = context
        initComponents()
    }

    internal fun initComponents() {
        mActivity?.application?.registerActivityLifecycleCallbacks(mLifecycle)
        splashSkipButtonBg.shape = GradientDrawable.OVAL
        splashSkipButtonBg.setColor(Color.parseColor("#66333333"))
        splashImageView = ImageView(mActivity)
        splashImageView?.scaleType = ImageView.ScaleType.FIT_XY
        splashImageView?.setBackgroundColor(mActivity!!.resources.getColor(android.R.color.white))
        val imageViewLayoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        this.addView(splashImageView, imageViewLayoutParams)
        splashImageView?.isClickable = true

        skipButton = TextView(mActivity)
        val skipButtonSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            skipButtonSizeInDip.toFloat(),
            mActivity!!.resources.displayMetrics
        ).toInt()
        val skipButtonLayoutParams = FrameLayout.LayoutParams(skipButtonSize, skipButtonSize)
        skipButtonLayoutParams.gravity = Gravity.TOP or Gravity.RIGHT
        val skipButtonMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            skipButtonMarginInDip.toFloat(),
            mActivity!!.resources.displayMetrics
        ).toInt()
        skipButtonLayoutParams.setMargins(0, skipButtonMargin, skipButtonMargin, 0)
        skipButton?.gravity = Gravity.CENTER
        skipButton?.setTextColor(mActivity!!.resources.getColor(android.R.color.white))
        skipButton?.setBackgroundDrawable(splashSkipButtonBg)
        skipButton?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
        this.addView(skipButton, skipButtonLayoutParams)

        skipButton?.setOnClickListener { dismissSplashView(true) }

        setDuration(duration)
        mHandler?.postDelayed(timerRunnable, delayTime.toLong())
    }

    private fun setImgUrl(imgUrl: String?) {
        this.imgUrl = imgUrl
    }

    private fun setActUrl(actUrl: String?) {
        this.actUrl = actUrl
    }

    private fun setDuration(duration: Int) {
        this.duration = duration
        skipButton?.text = String.format("%d 跳过", duration)
    }

    private fun setOnSplashImageClickListener(listener: OnSplashViewActionListener?) {
        if (null == listener) return
        mOnSplashViewActionListener = listener
        splashImageView?.setOnClickListener { listener.onSplashImageClick(actUrl) }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun dismissSplashView(initiativeDismiss: Boolean) {
        if (null != mOnSplashViewActionListener) mOnSplashViewActionListener!!.onSplashViewDismiss(initiativeDismiss)
        mHandler?.removeCallbacks(timerRunnable)
        val parent = this.parent as ViewGroup
        if (null != parent) {
            @SuppressLint("ObjectAnimatorBinding") val animator =
                ObjectAnimator.ofFloat(this@SplashView, "scale", 0.0f, 0.5f).setDuration(600)
            animator.start()
            animator.addUpdateListener { animation ->
                val cVal = animation.animatedValue as Float
                this@SplashView.alpha = 1.0f - 2.0f * cVal
                this@SplashView.scaleX = 1.0f + cVal
                this@SplashView.scaleY = 1.0f + cVal
            }
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    parent.removeView(this@SplashView)
                    showSystemUi()
                }

                override fun onAnimationCancel(animation: Animator) {
                    parent.removeView(this@SplashView)
                    showSystemUi()
                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
        }
    }

    fun showSystemUi() {
        mActivity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (mActivity is AppCompatActivity) {
            val supportActionBar = (mActivity as AppCompatActivity).supportActionBar
            if (null != supportActionBar) {
                if (isActionBarShowing) supportActionBar.show()
            }
        } else if (mActivity is Activity) {
            val actionBar = mActivity!!.actionBar
            if (null != actionBar) {
                if (isActionBarShowing) actionBar.show()
            }
        }
    }

    interface OnSplashViewActionListener {
        fun onSplashImageClick(actionUrl: String?)
        fun onSplashViewDismiss(initiativeDismiss: Boolean)
    }

    companion object {

        private val IMG_URL = "splash_img_url"
        private val ACT_URL = "splash_act_url"
        private var IMG_PATH: String? = null
        private val SP_NAME = "splash"
        private val skipButtonSizeInDip = 36
        private val skipButtonMarginInDip = 16
        private val delayTime = 1000   // 每隔1000 毫秒执行一次

        /**
         * static method, show splashView on above of the activity
         * you should called after setContentView()
         * @param activity  activity instance
         * @param durationTime  time to countDown
         * @param defaultBitmapRes  if there's no cached bitmap, show this default bitmap;
         * if null == defaultBitmapRes, then will not show the splashView
         * @param listener  splash view listener contains onImageClick and onDismiss
         */
        @SuppressLint("RestrictedApi")
        fun showSplashView(
            activity: Activity,
            durationTime: Int?,
            defaultBitmapRes: Int?,
            listener: OnSplashViewActionListener?
        ) {

            val contentView = activity.window.decorView.findViewById<View>(android.R.id.content) as ViewGroup
            if (null == contentView || 0 == contentView.childCount) {
                throw IllegalStateException("You should call showSplashView() after setContentView() in Activity instance")
            }
            IMG_PATH = activity.filesDir.absolutePath.toString() + "/splash_img.jpg"
            val splashView = SplashView(activity)
            val param =
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            splashView.setOnSplashImageClickListener(listener)
            if (null != durationTime) splashView.setDuration(durationTime)
            var bitmapToShow: Bitmap? = null

            if (isExistsLocalSplashData(activity)) {
                bitmapToShow = BitmapFactory.decodeFile(IMG_PATH)
                val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                splashView.setImgUrl(sp.getString(IMG_URL, null))
                splashView.setActUrl(sp.getString(ACT_URL, null))
                if (null == bitmapToShow) return
                splashView.setImage(bitmapToShow)
            } else if (null != defaultBitmapRes) {
                splashView.setImage(defaultBitmapRes)
            }

            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            if (activity is AppCompatActivity) {
                val supportActionBar = activity.supportActionBar
                if (null != supportActionBar) {
                    supportActionBar.setShowHideAnimationEnabled(false)
                    splashView.isActionBarShowing = supportActionBar.isShowing
                    supportActionBar.hide()
                }
            } else if (activity is Activity) {
                val actionBar = activity.actionBar
                if (null != actionBar) {
                    splashView.isActionBarShowing = actionBar.isShowing
                    actionBar.hide()
                }
            }
            contentView.addView(splashView, param)
        }

        /**
         * simple way to show splash view, set all non-able param as non
         * @param activity
         */
        fun simpleShowSplashView(activity: Activity) {
            showSplashView(activity, null, null, null)
        }

        private fun isExistsLocalSplashData(activity: Activity): Boolean {
            val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val imgUrl = sp.getString(IMG_URL, null)
            return !TextUtils.isEmpty(imgUrl) && isFileExist(IMG_PATH)
        }

        /**
         * static method, update splash view data
         * @param imgUrl - url of image which you want to set as splash image
         * @param actionUrl - related action url, such as webView etc.
         */
        fun updateSplashData(activity: Activity, imgUrl: String, actionUrl: String?) {
            IMG_PATH = activity.filesDir.absolutePath.toString() + "/splash_img.jpg"

            val editor = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
            editor.putString(IMG_URL, imgUrl)
            editor.putString(ACT_URL, actionUrl)
            editor.apply()

            getAndSaveNetWorkBitmap(imgUrl)
        }

        private fun getAndSaveNetWorkBitmap(urlString: String) {
            val getAndSaveImageRunnable = Runnable {
                var imgUrl: URL? = null
                var bitmap: Bitmap? = null
                try {
                    imgUrl = URL(urlString)
                    val urlConn = imgUrl.openConnection() as HttpURLConnection
                    urlConn.doInput = true
                    urlConn.connect()
                    val `is` = urlConn.inputStream
                    bitmap = BitmapFactory.decodeStream(`is`)
                    `is`.close()
                    saveBitmapFile(bitmap, IMG_PATH)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            Thread(getAndSaveImageRunnable).start()
        }

        @Throws(IOException::class)
        private fun saveBitmapFile(bm: Bitmap?, filePath: String?) {
            val myCaptureFile = File(filePath!!)
            val bos = BufferedOutputStream(FileOutputStream(myCaptureFile))
            bm!!.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
        }

        private fun isFileExist(filePath: String?): Boolean {
            return if (TextUtils.isEmpty(filePath)) {
                false
            } else {
                val file = File(filePath!!)
                file.exists() && file.isFile
            }
        }
    }
}
