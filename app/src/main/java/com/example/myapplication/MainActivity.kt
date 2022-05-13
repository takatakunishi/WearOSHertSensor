package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import kotlin.properties.Delegates

class MainActivity : WearableActivity(), SensorEventListener {
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //センサーマネージャーを取得する
        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //加速度計のセンサーを取得する
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT)
        // Enables Always-on
        setAmbientEnabled()
    }
    //センサーに何かしらのイベントが発生したときに呼ばれる
    override fun onSensorChanged(event: SensorEvent?) {
        //3つの値が配列で入ってくる
        Log.v("nullpo", "______")
        //X軸方法
        Log.v("nullpo", event?.values!![0].toString())
        //Y軸方法
        Log.v("nullpo", event?.values!![1].toString())
        //Z軸方法
        Log.v("nullpo", event?.values!![2].toString())
    }

    //センサー精度が変更されたときに発生するイベント
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    //アクティビティが閉じられたときにリスナーを解除する
    override fun onPause() {
        super.onPause()
        //リスナーを解除しないとバックグラウンドにいるとき常にコールバックされ続ける
        mManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        //リスナーとセンサーオブジェクトを渡す
        //第一引数はインターフェースを継承したクラス、今回はthis
        //第二引数は取得したセンサーオブジェクト
        //第三引数は更新頻度 UIはUI表示向き、FASTはできるだけ早く、GAMEはゲーム向き
        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
    }
}