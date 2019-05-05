package cn.woochen.common_ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.util.TypedValue
import org.w3c.dom.Node


class NodeProgress : View {

    private lateinit var mCirclePaint: Paint
    private lateinit var mLinePaint: Paint
    private lateinit var mTextPaint: Paint
    private var mNodes = mutableListOf<NodeElement>()

    //节点圆半径
    private var mCircleRadius = 20f
    //节点圆聚焦的颜色
    private val mCircleFocusColor: Int = Color.BLUE
    //节点圆正常的颜色
    private val mCircleNormalColor: Int = Color.GRAY
    //节点之间的距离
    private var mCircleSpacing = 40f


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //1.测量
        //2.画圆
        //3.画线
        //4.画文字
        initPaints()
    }


    private fun initPaints() {
        //circle paint
        mCirclePaint = Paint()
        mCirclePaint.isAntiAlias = true
        //line paint
        mLinePaint = Paint()
        mCirclePaint.isAntiAlias = true

        //text paint
        mTextPaint = Paint()
        mCirclePaint.isAntiAlias = true

    }


    fun setAdapter(nodes: List<NodeElement>) {
        mNodes.addAll(nodes)
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


    }


    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        if (!mNodes.isEmpty()) {
            for (index in mNodes.indices) {
                drawCircle(index, canvas)
                drawLine(index, canvas)
                drawText(index, canvas)
            }
        }
        /*  val paint = Paint()
          paint.color = Color.RED
          canvas.drawCircle(dp2Px(10f), dp2Px(10f), dp2Px(10f), paint)
          canvas.drawLine(dp2Px(20f), dp2Px(10f), dp2Px(40f), dp2Px(10f), paint)
          val fontMetricsInt = paint.fontMetricsInt
          val baseLine = dp2Px(10f) + (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
          canvas.drawText("湖北省", dp2Px(40f), baseLine, paint)*/
    }

    private fun drawText(index: Int, canvas: Canvas) {
        if (mNodes[index].isSelect) {
            mTextPaint.color = mCircleFocusColor
        } else {
            mTextPaint.color = mCircleNormalColor
        }
        val fontMetricsInt = mTextPaint.fontMetricsInt
        var centerY = mCircleRadius * (2 * index + 1)
        if (index > 0) centerY += mCircleSpacing * index
        val baseLine = centerY + (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        canvas.drawText(mNodes[index].content, mCircleRadius * 4, baseLine, mTextPaint)
    }

    private fun drawLine(index: Int, canvas: Canvas) {
        if (mNodes[index].isSelect) {
            mLinePaint.color = mCircleFocusColor
        } else {
            mLinePaint.color = mCircleNormalColor
        }
        //2r 4r+l 3r+2l
        var ly = 2 * mCircleRadius * (index + 1)
        if (index > 0) ly += mCircleSpacing * index
        Log.e("eee line", "$ly index ->$index")
        canvas.drawLine(mCircleRadius, ly, mCircleRadius, ly + mCircleSpacing, mLinePaint)
    }

    private fun drawCircle(index: Int, canvas: Canvas) {
        if (mNodes[index].isSelect) {
            mCirclePaint.color = mCircleFocusColor
        } else {
            mCirclePaint.color = mCircleNormalColor
        }
        //r 3r+l 5r+2l
        var cy = mCircleRadius * (2 * index + 1)
        if (index > 0) cy += mCircleSpacing * index
        Log.e("eee circle", cy.toString())
        canvas.drawCircle(mCircleRadius, cy, mCircleRadius, mCirclePaint)
    }


    private fun dp2Px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }


    class NodeElement(
        var isSelect: Boolean = false,
        var content: String = ""
    )


}