package cn.woochen.common_ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window

/**
 * 项目名称：joke
 * 类描述：（仅在包内使用）
 * 创建人：chenwuchao
 * 创建时间：2017/5/9 15:03
 */
internal class AlertController(
    /**
         * 获取diaglog
         *
         * @return
         */
        val dialog: AlertDialog,
    /**
         * 获取diaglog的window
         *
         * @return
         */
        val window: Window) {

    private var viewHelper: DialogViewHelper? = null

    fun setViewHelper(viewHelper: DialogViewHelper) {
        this.viewHelper = viewHelper
    }

    /**
     * 设置文本
     * @param resId
     * @param charSequence
     */
    fun setText(resId: Int, charSequence: CharSequence) {
        viewHelper!!.setText(resId, charSequence)
    }

    /**
     * 在集合中通过id找到控件，没有就保存进集合中
     * @return
     * @param resId
     */
    fun <T : View> getView(resId: Int): T? {
        return viewHelper!!.getView(resId)
    }

    /**
     * 设置点击事件
     * @param resId
     * @param onClickListener
     */
    fun setOnClickListener(resId: Int, onClickListener: View.OnClickListener) {
        viewHelper!!.setOnClickListener(resId, onClickListener)
    }

    class AlertParams(val mContext: Context, val mThemeResId: Int) {
        //默认点击空白可以取消
        var mCancelable = true
        //取消监听
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        //销毁监听
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        //键盘按下监听
        var mOnKeyListener: DialogInterface.OnKeyListener? = null
        //dialog布局
        var mView: View? = null
        //dialog布局id
        var mViewLayoutResId: Int = 0

        //文本集合保存
        var textArrays = SparseArray<CharSequence>()
        //点击事件保存
        var clickArrays = SparseArray<View.OnClickListener>()
        //diaglog布局处理类
        private var viewHelper: DialogViewHelper? = null
        //宽度
        var mWidth = ViewGroup.LayoutParams.MATCH_PARENT
        //高度
        var mHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        //位置
        var mGravity = Gravity.CENTER
        var mAnimation = 0

        /**
         * 绑定参数
         *
         * @param mAlert
         */
        fun apply(mAlert: AlertController) {
            //1.设置布局
            if (mViewLayoutResId != 0) {
                //开发者传入了资源id
                viewHelper = DialogViewHelper(mContext, mViewLayoutResId)
            }
            if (mView != null) {
                //开发者传入了视图
                viewHelper = DialogViewHelper()
                viewHelper!!.contentView = mView
            }
            if (viewHelper == null) {
                //设置布局方法没有调用
                throw IllegalArgumentException("请设置布局setContentView")
            }
            //给dialog设置布局
            mAlert.dialog.setContentView(viewHelper!!.contentView)

            //设置viewHelper
            mAlert.setViewHelper(viewHelper!!)

            //2.设置文本
            for (i in 0 until textArrays.size()) {
                viewHelper!!.setText(textArrays.keyAt(i), textArrays.valueAt(i))
            }
            //3.设置点击事件
            for (i in 0 until clickArrays.size()) {
                viewHelper!!.setOnClickListener(clickArrays.keyAt(i), clickArrays.valueAt(i))
            }
            //4.设置自定义效果
            val window = mAlert.window
            //设置位置
            window.setGravity(mGravity)
            //设置动画
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation)
            }
            //设置宽高
            val attributes = window.attributes
            attributes.width = mWidth
            attributes.height = mHeight
            window.attributes = attributes
        }
    }
}
