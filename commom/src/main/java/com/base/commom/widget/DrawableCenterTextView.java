package com.base.commom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Administrator on 2018/7/19.
 */

public class DrawableCenterTextView extends AppCompatTextView {
    private Drawable[] drawables;
    public DrawableCenterTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        drawables = getCompoundDrawables();
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableCenterTextView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
//            if(drawableLeft == null){
//                drawableLeft = drawables[2];// 右面的drawable
//            }
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }else{
                drawableLeft = drawables[2];
                if (drawableLeft != null) {
                    float textWidth = getPaint().measureText(getText().toString());
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableWidth = 0;
                    drawableWidth = drawableLeft.getIntrinsicWidth();
                    float bodyWidth = textWidth + drawableWidth + drawablePadding;
                    setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                    canvas.translate((getWidth() - bodyWidth) / 2, 0);
                }else {
                    drawableLeft = drawables[1];
                    if(drawableLeft != null) {
//                        float textWidth = getPaint().measureText(getText().toString());
//                        int drawablePadding = getCompoundDrawablePadding();
//                        int drawableWidth = 0;
//                        drawableWidth = drawableLeft.getIntrinsicWidth();
//                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
//                        canvas.translate((getWidth() - bodyWidth) / 2, 0);
                        // 否则如果上边的Drawable不为空时，获取文本的高度
                        Rect rect = new Rect();
                        getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
                        float textHeight = rect.height();
                        int drawablePadding = getCompoundDrawablePadding();
                        int drawableHeight = drawableLeft.getIntrinsicHeight();
                        // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                        float bodyHeight = textHeight + drawablePadding + drawableHeight;
                        // 移动画布开始绘制的Y轴
                        canvas.translate(0, (getHeight() - bodyHeight) / 2);

                    }
                }

            }
        }
        super.onDraw(canvas);
    }
}
