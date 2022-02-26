package io.dev00.contexttest

import android.app.ActivityManager
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.dev00.contexttest.SensorEvents.SensorEvents
import io.dev00.contexttest.Services.MyBackgroundService
import io.dev00.contexttest.Services.MyForegroundService
import io.dev00.contexttest.ui.theme.ContextTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContextTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SensorComp()
                }
            }
        }
    }
}

@Composable
fun SensorComp() {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    var lightValue by remember {
        mutableStateOf(0f)
    }
//    val service = Intent(context,MyBackgroundService::class.java)
//    context.startService(service)
    val service = Intent(context, MyForegroundService::class.java)
    if (!isServiceRunning(context = context, serviceName = MyForegroundService::class.java.name)) {
        context.startForegroundService(service)
    }
    val lightSensorListener = remember {
        mutableStateOf(
            SensorEvents(
                sensorManager = sensorManager,
                sensorType = Sensor.TYPE_LIGHT,
                sensorChanged = {
                    if (it != null) {
                        lightValue = it.values[0]
                    }
                }
            )
        )
    }
    Row() {
        Text(text = "Light Value")
        Text(text = lightValue.toString())
    }
}

fun isServiceRunning(context: Context,serviceName:String): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.getRunningServices(Int.MAX_VALUE).forEach {
        if (serviceName.equals(it.service.className)) {
            return true
        }
    }
    return false
}


