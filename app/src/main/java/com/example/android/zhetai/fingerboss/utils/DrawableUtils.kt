package com.example.android.zhetai.fingerboss.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import java.util.*

object DrawableUtils {

    fun tint(drawable: Drawable, color: Int): Drawable {
        val wrappedDrawable: Drawable = DrawableCompat.wrap(drawable)
        wrappedDrawable.mutate()
        DrawableCompat.setTint(wrappedDrawable, color)
        return wrappedDrawable
    }

    fun randomColorGenerator(): Int {
        val colorRandom = Random()
        val r = colorRandom.nextInt(255) + 1
        val g = colorRandom.nextInt(255) + 1
        val b = colorRandom.nextInt(255) + 1
        return Color.rgb(r, g, b)
    }

    fun getBitmapFromVectorDrawable(drawable: Drawable?): Bitmap? {
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