package com.potato.ykeepaccount.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.base.commom.utils.LogUtil

class HomeArcView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //控件宽度
    private var mWidth = 0
    //控件高度
    private var mHeight = 0

    private lateinit var mRect : Rect
    private lateinit var mBottomRectF : RectF
    private lateinit var mTopRectF : RectF
    private lateinit var mTopArcRectF : RectF
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    init {
        setBackgroundColor(Color.TRANSPARENT)

        mPaint.color = Color.WHITE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        if(!this::mRect.isInitialized){
            mRect = Rect(0, mHeight / 2, mWidth - (mHeight / 2), mHeight)
            mBottomRectF = RectF(mWidth - mHeight.toFloat(), mHeight /2f, mWidth.toFloat(), mHeight.toFloat() + mHeight /2)
            mTopRectF = RectF(0f, - mHeight.toFloat() / 2, (mHeight.toFloat() -1) / 2, mHeight.toFloat() /2)
            mTopArcRectF = RectF(0f, - mHeight.toFloat() / 2, mHeight.toFloat(), mHeight.toFloat() /2)
        }
        LogUtil.i("width: $mWidth, height: $mHeight")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(mRect, mPaint)
        canvas?.drawArc(mBottomRectF, 270f, 90f, true, mPaint);

        setLayerType(LAYER_TYPE_HARDWARE, mPaint)
//        mPaint.color = Color.BLACK
        canvas?.drawRect(mTopRectF, mPaint);
        mPaint.xfermode = xfermode
//        mPaint.color = Color.WHITE
        canvas?.drawArc(mTopArcRectF, 90f, 90f, true, mPaint);
        mPaint.xfermode = null

    }

}