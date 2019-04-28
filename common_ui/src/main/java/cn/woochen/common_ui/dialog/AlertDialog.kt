package cn.woochen.common_ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import cn.woochen.common_ui.R


/**
 * 项目名称：joke
 * 类描述：自定义dialog
 * 创建人：chenwuchao
 * 创建时间：2017/5/9 15:03
 */
class AlertDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private val mAlert: AlertController

    init {
        mAlert = AlertController(this, window!!)
    }

    /**
     * 设置文本
     *
     * @param resId
     * @param charSequence
     */
    fun setText(resId: Int, charSequence: CharSequence) {
        mAlert.setText(resId, charSequence)
    }

    /**
     * 在集合中通过id找到控件，没有就保存进集合中
     *
     * @param resId
     * @return
     */
    fun <T : View> getView(resId: Int): T? {
        return mAlert.getView(resId)
    }

    /**
     * 设置点击事件
     *
     * @param resId
     * @param onClickListener
     */
    fun setOnClickListener(resId: Int, onClickListener: View.OnClickListener) {
        mAlert.setOnClickListener(resId, onClickListener)
    }


    class Builder @JvmOverloads constructor(context: Context, private val mTheme: Int = R.style.dialog) {
        private val P: AlertController.AlertParams
        private var mDialog: AlertDialog? = null

        /**
         * 获取dialog
         *
         * @return
         */
        val dialog: AlertDialog
            get() {
                if (mDialog == null) {
                    throw IllegalArgumentException("please create dialog first!")
                }
                return mDialog as AlertDialog
            }

        init {
            P = AlertController.AlertParams(context, mTheme)
        }

        /**
         * 设置布局
         *
         * @param layoutResId
         * @return
         */
        fun setContentView(layoutResId: Int): Builder {
            P.mView = null
            P.mViewLayoutResId = layoutResId
            return this
        }

        /**
         * 设置布局
         *
         * @param view
         * @return
         */
        fun setContentView(view: View): Builder {
            P.mView = view
            P.mViewLayoutResId = 0
            return this
        }

        /**
         * 取消监听
         *
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }

        /**
         * 销毁监听
         *
         * @param onDismissListener
         * @return
         */
        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }

        /**
         * 键盘按下监听
         *
         * @param onKeyListener
         * @return
         */
        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener): Builder {
            P.mOnKeyListener = onKeyListener
            return this
        }


        /**
         * 设置文本
         *
         * @param resId 资源id
         * @param text  文本
         * @return
         */
        fun setText(resId: Int, text: CharSequence): Builder {
            P.textArrays.put(resId, text)
            return this
        }

        /**
         * 设置点击事件
         *
         * @param resId
         * @param onClickListener
         * @return
         */
        fun setOnClickListener(resId: Int, onClickListener: View.OnClickListener): Builder {
            P.clickArrays.put(resId, onClickListener)
            return this
        }


        /**
         * 设置全屏
         *
         * @return
         */
        fun setFullWidth(): Builder {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }


        /**
         * 设置宽高
         *
         * @param width  宽度
         * @param height 高度
         * @return
         */
        fun setWidthAndHeight(width: Int, height: Int): Builder {
            P.mWidth = width
            P.mHeight = height
            return this
        }


        /**
         * 设置从底部弹出
         *
         * @param isAnimation 是否有动画
         * @return
         */
        fun fromBottom(isAnimation: Boolean): Builder {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_form_bottm_anim
            }
            P.mGravity = Gravity.BOTTOM
            return this
        }

        /**
         * 设置默认动画
         *
         * @return
         */
        fun addDefaultAnimation(): Builder {
            //            P.mAnimation = R.style.dialog_scale_anim;
            return this
        }

        /**
         * 设置自定义动画
         *
         * @param styleAnimation
         * @return
         */
        fun addAnimation(styleAnimation: Int): Builder {
            P.mAnimation = styleAnimation
            return this
        }


        /**
         * 创建dialog
         *
         * @return
         */
        fun create(): AlertDialog {
            val dialog = AlertDialog(P.mContext, mTheme)
            P.apply(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnCancelListener(P.mOnCancelListener)
            dialog.setOnDismissListener(P.mOnDismissListener)
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener)
            }
            //返回dialog
            mDialog = dialog
            return dialog
        }

        /**
         * 显示dialog
         *
         * @return
         */
        fun show(): AlertDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }

    }
}
