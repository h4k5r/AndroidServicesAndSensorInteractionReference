package io.dev00.contexttest.SensorEvents

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log


class SensorEvents(
    sensorManager: SensorManager,
    sensorType: Int,
    wakeup: Boolean = false,
    accuracyChanged: (sensor: Sensor?, accuracy: Int) -> Unit = { _: Sensor?, _: Int -> },
    sensorChanged: (event: SensorEvent?) -> Unit = {}

) : SensorEventListener {
    private var sensor: Sensor = sensorManager.getDefaultSensor(sensorType, wakeup)
    private var sensorChanged: (event: SensorEvent?) -> Unit
    private var accuracyChanged: (sensor: Sensor?, accuracy: Int) -> Unit
    private var sensorManager:SensorManager

    init {
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_FASTEST,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        this.accuracyChanged = accuracyChanged
        this.sensorChanged = sensorChanged
        this.sensorManager = sensorManager
    }

    override fun onSensorChanged(event: SensorEvent?) {
        this.sensorChanged(event)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        this.accuracyChanged(sensor, accuracy)
    }
    fun unregister() {
        sensorManager.unregisterListener(this)
    }
}

