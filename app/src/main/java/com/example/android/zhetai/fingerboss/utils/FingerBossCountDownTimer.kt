package com.example.android.zhetai.fingerboss.utils

import android.os.CountDownTimer
import android.util.Log
import com.example.android.zhetai.fingerboss.manager.TouchAreaManager

class FingerBossCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    private val touchAreaManager: TouchAreaManager
) :
    CountDownTimer(millisInFuture, countDownInterval) {
    private var timeShow = TIMER

    override fun onFinish() {
        timeShow = TIMER
    }

    override fun onTick(millisUntilFinished: Long) {
        Log.v("1", "$millisUntilFinished")
        if (timeShow > 0) touchAreaManager.updateTimer(timeShow)
        if (timeShow == 0) {
            touchAreaManager.showResult()
            timeShow = -1
        }
        timeShow--
    }

    companion object {
        const val TIMER = 3
        const val COUNT_DOWN_INTERVAL = 1
    }
}