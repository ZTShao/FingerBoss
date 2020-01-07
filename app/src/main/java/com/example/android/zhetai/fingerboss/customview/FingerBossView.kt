package com.example.android.zhetai.fingerboss.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import com.example.android.zhetai.fingerboss.R
import com.example.android.zhetai.fingerboss.utils.DrawableUtils
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

        //height和width是整个屏幕的？？why
        val pxValueX = MathUtils.dip2px(context, 96f) / 2
        val pxValueY = pxValueX * 1.4
        val drawingCenterX = event.rawX - pxValueX
        val drawingCenterY = (event.rawY - pxValueY).toFloat()

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, drawingCenterX, drawingCenterY, painter)
        }
    }

    private fun getBitmapFromVectorDrawable(drawable: Drawable?): Bitmap? {
        return if (drawable != null) {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } else null
    }
}