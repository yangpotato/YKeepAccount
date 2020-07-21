package com.potato.ykeepaccount.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes
import kotlin.math.min

class WaveView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr){
    private var mHeight: Int = 0
    private var mWidth : Int = 0

    private val mWavePaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mCirclePaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mProgress = 50
    private lateinit var mStartPoint: Point
    private val mCycle = 160

    init {
        mWavePaint.style = Paint.Style.FILL
        mWavePaint.color = Color.parseColor("#1998FA")

        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.color = Color.parseColor("#1998FA")

        mTextPaint.textSize = 50f
        mTextPaint.color = Color.BLUE

        mStartPoint = Point(-mCycle * 4, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mWidth = measureSize(400, widthMeasureSpec)
        mHeight = measureSize(400, heightMeasureSpec)
        mWidth = min(mWidth, mHeight)
        mHeight = mWidth
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int{
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return when(mode){
            MeasureSpec.UNSPECIFIED -> defaultSize
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> 0
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        clipCircle(canvas)

        canvas?.drawCircle(mWidth / 2f, mHeight / 2f, mWidth / 2f, mCirclePaint)

        drawWavePath(canvas)
    }

    private fun drawWavePath(canvas: Canvas?) {
//        mStartPoint.y =
    }

    /**
     * 裁剪画布为圆形
     */
    private fun clipCircle(canvas: Canvas?) {
        val circlePath = Path()
        circlePath.addCircle(mWidth / 2f, mHeight / 2f, mWidth / 2f, Path.Direction.CW)
        canvas?.clipPath(circlePath)
    }

}