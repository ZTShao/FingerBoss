package com.example.android.zhetai.fingerboss.customview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.android.zhetai.fingerboss.R
import com.example.android.zhetai.fingerboss.utils.FingerBossCountDownTimer
import java.util.*
import kotlin.collections.ArrayList

class TouchAreaManager(private val context: Context, val layout: ConstraintLayout) {
    private var countDownTimer: FingerBossCountDownTimer = FingerBossCountDownTimer(
        FingerBossCountDownTimer.TOTAL_COUNT_DOWN_TIME * 1000L,
        FingerBossCountDownTimer.COUNT_DOWN_INTERVAL * 1000L,
        this
    )
    private val touchArea: View = layout.findViewById(R.id.view_touch_area)
    private val timerDisplayTextView: TextView = layout.findViewById(R.id.text_view_timer_display)

    private var fingerBossViews: MutableList<FingerBossView> = ArrayList()
    private val fingerBossViewAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.anim_fingerbossview_blink)

    private var winnerForThisTime: Int = Int.MAX_VALUE
    private var isReady: Boolean = false

    init {
        touchArea.setOnTouchListener { _, event ->
            if (!isReady) {
                Log.v(TAG, "action${event.action}")
                when (event.actionMasked) {

                    MotionEvent.ACTION_DOWN -> addAFingerBossView(event)
                    MotionEvent.ACTION_UP -> {
                        /*isReady = true
                        countDownTimer.start()*/
                    }
                    else -> {
                    }
                }
            }
            true
        }
    }

    private fun addAFingerBossView(event: MotionEvent): Boolean {
        Log.v(TAG, "addNewFinger${event.rawX}")
        Log.v(TAG, "addNewFinger${event.rawY}")

        val newFingerBossView = FingerBossView(context, event)
        fingerBossViews.add(newFingerBossView)
        layout.addView(newFingerBossView)
        newFingerBossView.animation = fingerBossViewAnimation
        return true
    }

    fun showResult() {
        Log.v("2", "showresult")
        winnerForThisTime = Random().nextInt() % fingerBossViews.size
        for (i in 0 until fingerBossViews.size) {
            if (i != winnerForThisTime) {
                fingerBossViews[i].visibility = View.GONE
            }
        }

    }

    fun updateTimer(time: Int) {
        Log.v("1", "update$time")
        timerDisplayTextView.text = time.toString()
    }

    fun recycle() {
        fingerBossViews[winnerForThisTime].visibility = View.GONE
        timerDisplayTextView.text = ""
        fingerBossViews = ArrayList()
        countDownTimer.cancel()
        isReady = false
    }

    companion object {
        const val TAG = "TouchAreaManager"
    }
}