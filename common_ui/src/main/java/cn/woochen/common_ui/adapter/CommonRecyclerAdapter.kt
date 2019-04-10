package cn.woochen.common_ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * 类描述：万能RecyclerView适配器
 * 创建人：woochen123
 * 创建时间：2017/5/23 18:32
 */
abstract class CommonRecyclerAdapter<T>(protected var mContext: Context, //数据怎么办？利用泛型
                                        protected var mDatas: List<T>, // 布局怎么办？直接从构造里面传递
                                        private var mLayoutId: Int) : RecyclerView.Adapter<CommonRecyclerAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    // 多布局支持
    private var mMultiTypeSupport: MultiTypeSupport<T>? = null
    //单击
    var mItemClickListener: OnItemClickListener? = null
    //长按
    var mLongClickListener: OnLongClickListener? = null
    //悬浮
    var mHoverListener: View.OnHoverListener? = null

    /**
     * 多布局支持
     */
    constructor(context: Context, data: List<T>, multiTypeSupport: MultiTypeSupport<T>) : this(context, data, -1) {
        this.mMultiTypeSupport = multiTypeSupport
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    override fun getItemViewType(position: Int): Int {
        // 多布局支持
        return mMultiTypeSupport?.getLayoutId(mDatas[position], position) ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType
        }

        val itemView = mInflater.inflate(mLayoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(position) }
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener { mLongClickListener!!.onLongClick(position) }
        }
        if (mHoverListener != null) {
            holder.itemView.setOnHoverListener { view, motionEvent -> mHoverListener!!.onHover(view, motionEvent) }
        }
        convert(holder, mDatas[position])
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     * @param item 当前的数据
     */
    abstract fun convert(holder: ViewHolder, item: T)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 用来存放子View减少findViewById的次数
        private val mViews: SparseArray<View> = SparseArray()

        /**
         * 设置TextView文本
         */
        fun setText(viewId: Int, text: CharSequence): ViewHolder {
            val tv = getView<TextView>(viewId)
            tv.text = text
            return this
        }

        /**
         * 通过id获取view
         */
        fun <T : View> getView(viewId: Int): T {
            // 先从缓存中找
            var view: View? = mViews.get(viewId)
            if (view == null) {
                // 直接从ItemView中找
                view = itemView.findViewById(viewId)
                mViews.put(viewId, view)
            }
            return view as T
        }

        /**
         * 设置View的Visibility
         */
        fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
            getView<View>(viewId).visibility = visibility
            return this
        }

        /**
         * 设置ImageView的资源
         */
        fun setImageResource(viewId: Int, resourceId: Int): ViewHolder {
            val imageView = getView<ImageView>(viewId)
            imageView.setImageResource(resourceId)
            return this
        }

        /**
         * 设置条目点击事件
         */
        fun setOnIntemClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        /**
         * 设置条目长按事件
         */
        fun setOnIntemLongClickListener(listener: View.OnLongClickListener) {
            itemView.setOnLongClickListener(listener)
        }

        /**
         * 设置条目悬浮事件
         */
        fun setHoverListener(hoverListener: View.OnHoverListener) {
            itemView.setOnHoverListener(hoverListener)
        }

    }

}
