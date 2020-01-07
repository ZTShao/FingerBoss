package com.example.android.zhetai.fingerboss.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.example.android.zhetai.fingerboss.R
import com.example.android.zhetai.fingerboss.utils.DrawableUtils
import com.example.android.zhetai.fingerboss.utils.DrawableUtils.getBitmapFromVectorDrawable
import com.example.android.zhetai.fingerboss.utils.MathUtils

@SuppressLint("ViewConstructor")
class FingerBossView(context: Context?, private val event: MotionEvent) : View(context) {
    private val painter: Paint = Paint()
    private var bitmap: Bitmap? = null

    init {
        bitmap = getBitmapFromVectorDrawable(
            DrawableUtils.tint(
                resources.getDrawable(R.drawable.ic_fingerboss_touch_image),
                DrawableUtils.randomColorGenerator()
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //手动调整坐标
        val pxValueX = MathUtils.dip2px(context, 96f) / 2
        val pxValueY = pxValueX * 1.4
        val drawingCenterX = event.rawX - pxValueX
        val drawingCenterY = (event.rawY - pxValueY).toFloat()

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, drawingCenterX, drawingCenterY, painter)
        }
    }


    companion object {
        const val TAG = "FingerBossView"
    }
}