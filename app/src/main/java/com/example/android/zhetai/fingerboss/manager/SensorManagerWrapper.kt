package com.example.android.zhetai.fingerboss.manager

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class SensorManagerWrapper(context: Context) : SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var onShakeListener: OnShakeListener? = null
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()
    private var lastZ: Float = 0.toFloat()
    private var lastUpdateTime: Long = 0

    init {
        sensorManager = context
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager != null) {
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        if (sensor != null) {
            sensorManager!!.registerListener(
                this, sensor,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    fun unregister() {
        sensorManager!!.unregisterListener(this)
    }

    interface OnShakeListener {
        fun onShake()
    }

    fun setOnShakeListener(listener: OnShakeListener) {
        onShakeListener = listener
    }

    override fun onSensorChanged(event: SensorEvent) {

        val currentUpdateTime = System.currentTimeMillis()
        val timeInterval = currentUpdateTime - lastUpdateTime
        if (timeInterval < UPDATE_INTERVAL_TIME) return
        lastUpdateTime = currentUpdateTime

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val deltaX = x - lastX
        val deltaY = y - lastY
        val deltaZ = z - lastZ

        lastX = x
        lastY = y
        lastZ = z
        val speed = Math.sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()) / timeInterval * 10000

        if (speed >= SPEED_TRESHOLD) {
            onShakeListener!!.onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    companion object {
        const val SPEED_TRESHOLD = 5000
        const val UPDATE_INTERVAL_TIME = 50
    }
}