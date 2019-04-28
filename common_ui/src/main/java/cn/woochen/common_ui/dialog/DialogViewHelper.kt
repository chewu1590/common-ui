package cn.woochen.common_ui.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import java.lang.ref.WeakReference

/**
 * 项目名称：joke
 * 类描述：专门处理界面
 * 创建人：chenwuchao
 * 创建时间：2017/5/9 15:03
 */
internal class DialogViewHelper() {
    /**
     * 获得dialog布局
     * @return
     */
    /**
     * 设置布局
     *
     * @param mView
     */
    var contentView: View? = null
    private val mViews: SparseArray<WeakReference<View>>

    init {
        mViews = SparseArray()
    }

    constructor(mContext: Context, mViewLayoutResId: Int) : this() {
        contentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null)
    }

    /**
     * 设置文本
     * @param resId
     * @param charSequence
     */
    fun setText(resId: Int, charSequence: CharSequence) {
        val textView = getView<TextView>(resId)
        if (textView != null) {
            textView.text = charSequence
        }
    }

    /**
     * 在集合中通过id找到控件，没有就保存进集合中
     * @return
     * @param resId
     */
    fun <T : View> getView(resId: Int): T? {
        //必须这么写 mViews.get(resId).get();会直接崩溃
        val weakReference = mViews.get(resId)
        var view: View? = null
        if (weakReference != null) {
            view = weakReference.get()
        }

        if (view == null) {
            //该id对应的控件集合中不存在
            view = contentView!!.findViewById(resId)
            if (view != null) {
                //控件存在,存入集合中
                mViews.put(resId, WeakReference(view))
            }
        }
        return view as T?
    }

    /**
     * 设置点击事件
     * @param resId
     * @param onClickListener
     */
    fun setOnClickListener(resId: Int, onClickListener: View.OnClickListener) {
        val view = getView<View>(resId)
        view?.setOnClickListener(onClickListener)
    }
}
