package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData

data class Hert(
        val hertrate: Float,
        val hertbeat:Float
)

// 向き情報を格納するクラス
data class Orientation(
        val azimuth: Float,
        val pitch: Float,
        val roll: Float
)

class HertLiveData(
        context: Context,
        private val sensorDelay: Int = SensorManager.SENSOR_DELAY_UI
    ):LiveData<Hert>(), SensorEventListener {
    private val mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rate: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
    private val beat: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT)
    private val magneticField: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private val accelerometer: Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    private val mRateReading = FloatArray(2)
    private val mBeatReading = FloatArray(2)
    private val mAccelerometerReading = FloatArray(3)
    private val mMagnetometerReading = FloatArray(3)

    private val mRotationMatrix = FloatArray(9)
    private val mOrientationAngles = FloatArray(3)
    private val mHeart = FloatArray(2)

    override fun onActive() {
        super.onActive()
        mSensorManager.registerListener(this, rate, SensorManager.SENSOR_DELAY_NORMAL, sensorDelay)
        mSensorManager.registerListener(this, beat, SensorManager.SENSOR_DELAY_NORMAL, sensorDelay)
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, sensorDelay)
        mSensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL, sensorDelay)
    }

    override fun onInactive() {
        super.onInactive()
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, mAccelerometerReading, 0, mAccelerometerReading.size)
        } else if (event.sensor == magneticField) {
            System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.size)
        } else if (event.sensor == rate) {
            System.arraycopy(event.values, 0, mRateReading, 0, mRateReading.size)
        } else if (event.sensor == beat) {
            System.arraycopy(event.values, 0, mBeatReading, 0, mBeatReading.size)
        }

        value = Hert(mHeart[0],mHeart[1])
    }
}
