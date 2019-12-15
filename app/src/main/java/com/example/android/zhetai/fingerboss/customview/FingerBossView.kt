package com.example.android.zhetai.fingerboss.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.android.zhetai.fingerboss.R

class FingerBossView(context: Context?, private val event: MotionEvent) : View(context) {
    private val painter: Paint = Paint()
    private var bitmap: Bitmap? = null

    init {
        bitmap = getBitmapFromVectorDrawable(resources.getDrawable(R.drawable.ic_fingerboss_touch_image))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //height和width是整个屏幕的？？why
        val drawingCenterX = event.rawX-100
        val drawingCenterY = event.rawY -100

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, drawingCenterX, drawingCenterY, painter)
        }
    }

/*    fun adjustWithMotionEvent(event: MotionEvent) {
        Xpoint = event.rawX
        Ypoint = event.rawY
        invalidate()
    }*/

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