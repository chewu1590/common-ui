package cn.woochen.common_ui.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.lang.IllegalArgumentException

/**
 * 底部页签基类(需要继承该类实现具体的布局)
 * @author woochen123
 * @time 2017/11/22 10:26
 * @desc
 */
abstract class TabItemView<T : TabItemView.Builder> protected constructor(protected var build: T) {

    protected var mTabItemView: View? = null

    /**
     * 得到条目
     */
    val tabView: View?
        get() {
            if (build.layoutId == 0) {
                throw IllegalArgumentException("please invoke setContentView first")
            }
            if (mTabItemView == null) {
                mTabItemView = LayoutInflater.from(build.context).inflate(build.layoutId, null)
                initView()
            }
            return mTabItemView
        }

    protected fun setText(layoutId: Int, msg: String) {
        val view = getView<TextView>(layoutId)
        view.text = msg
    }

    protected fun setImageRes(layoutId: Int, resId: Int) {
        val view = getView<ImageView>(layoutId)
        view.setImageResource(resId)
    }

    /**
     * 布局初始化
     */
    protected abstract fun initView()

    /**
     * 是否选择当前条目
     * @param selected
     */
    abstract fun setSelected(selected: Boolean)


    /**
     * 获得资源id的View
     */
    protected fun <T : View> getView(layoutId: Int): T {
        return mTabItemView!!.findViewById<View>(layoutId) as T
    }

    abstract class Builder {
        var context: Context
        var layoutId: Int = 0

        constructor(context: Context) {
            this.context = context
        }

        constructor(context: Context, layoutId: Int) {
            this.context = context
            this.layoutId = layoutId
        }

        fun setContentView(layoutId: Int): Builder {
            this.layoutId = layoutId
            return this
        }

        abstract fun <T : TabItemView<*>> build(): T


    }
}
