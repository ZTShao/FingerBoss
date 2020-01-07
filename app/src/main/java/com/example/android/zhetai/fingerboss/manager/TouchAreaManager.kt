package com.example.android.zhetai.fingerboss.manager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.android.zhetai.fingerboss.R
import com.example.android.zhetai.fingerboss.customview.FingerBossView
import com.example.android.zhetai.fingerboss.utils.FingerBossCountDownTimer
import java.util.*
import kotlin.collections.ArrayList

class TouchAreaManager(private val context: Context, val layout: ConstraintLayout) {

    private var countDownTimer: FingerBossCountDownTimer = FingerBossCountDownTimer(
        (FingerBossCountDownTimer.TIMER + 1) * 1000L,
        FingerBossCountDownTimer.COUNT_DOWN_INTERVAL * 1000L,
        this
    )
    private var fingerBossViews: MutableList<FingerBossView> = ArrayList()

    private val touchArea: View = layout.findViewById(R.id.view_touch_area)
    private var againButton: ImageView
    private val timerDisplayTextView: TextView = layout.findViewById(R.id.text_view_timer_display)

    private var winnerForThisTime: Int = Int.MAX_VALUE
    private var isReady: Boolean = false

    init {
        val sensor = SensorManagerWrapper(context)
        sensor.setOnShakeListener(object : SensorManagerWrapper.OnShakeListener {
            override fun onShake() {
                if (!isReady) {
                    isReady = true
                    countDownTimer.start()
                }
            }
        })

        touchArea.setOnTouchListener { _, event ->
            if (!isReady) {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> addAFingerBossView(event)
                    MotionEvent.ACTION_UP -> {
                    }
                    else -> {
                    }
                }
            }
            true
        }

        againButton = layout.findViewById(R.id.button_again)
        againButton.setOnClickListener {
            recycle()
        }
    }

    private fun addAFingerBossView(event: MotionEvent): Boolean {
        val newFingerBossView = FingerBossView(context, event)
        fingerBossViews.add(newFingerBossView)
        layout.addView(newFingerBossView)
        newFingerBossView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_fingerboss_blink))
        return true
    }

    fun showResult() {
        timerDisplayTextView.text = ""
        timerDisplayTextView.clearAnimation()
        winnerForThisTime = Math.abs(Random().nextInt()) % fingerBossViews.size
        for (i in 0 until fingerBossViews.size) {
            if (i != winnerForThisTime) {
                fingerBossViews[i].visibility = View.GONE
                fingerBossViews[i].clearAnimation()

            }
        }
        againButton.visibility = View.VISIBLE
    }

    fun updateTimer(time: Int) {
        timerDisplayTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_fingerboss_blink))
        timerDisplayTextView.text = time.toString()
    }

    private fun recycle() {
        fingerBossViews[winnerForThisTime].visibility = View.GONE
        fingerBossViews[winnerForThisTime].clearAnimation()
        fingerBossViews = ArrayList()
        countDownTimer.cancel()
        againButton.visibility = View.GONE
        isReady = false
    }

    companion object {
        const val TAG = "TouchAreaManager"
    }
}
