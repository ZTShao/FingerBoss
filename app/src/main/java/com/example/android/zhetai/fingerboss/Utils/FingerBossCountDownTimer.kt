package com.example.android.zhetai.fingerboss.Utils

import android.os.CountDownTimer
import android.util.Log
import com.example.android.zhetai.fingerboss.customview.TouchAreaManager

class FingerBossCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    private val touchAreaManager: TouchAreaManager
) :
    CountDownTimer(millisInFuture, countDownInterval) {
    private var timeShow = TIME_SHOW_RESULT

    override fun onFinish() {
        touchAreaManager.recycle()
        timeShow = TIME_SHOW_RESULT
    }

    override fun onTick(millisUntilFinished: Long) {
        Log.v("1", "$millisUntilFinished")
        if (timeShow > 0) touchAreaManager.updateTimer(timeShow--)
        if (timeShow == 0) {
            touchAreaManager.showResult()
            timeShow = -1
        }
    }

    companion object {
        private const val TIME_SHOW_RESULT = 3
        private const val TIME_REFRESH = 3
        const val TOTAL_COUNT_DOWN_TIME = TIME_REFRESH + TIME_SHOW_RESULT
        const val COUNT_DOWN_INTERVAL = 1
    }
}