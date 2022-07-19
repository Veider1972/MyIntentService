package ru.veider.myintentservice

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.veider.myintentservice.testservice.TestService1
import ru.veider.myintentservice.testservice.TestService2

const val TEXT_VIEW_1 = "TEXT_VIEW_1"
const val TEXT_VIEW_2 = "TEXT_VIEW_2"
const val STR_VALUE = "STR_VALUE"
const val INT_VALUE = "INT_VALUE"
const val INT_VALUE1 = "INT_VALUE1"
const val MY_INTENT = "MY_INTENT"


class MainActivity : AppCompatActivity() {


    lateinit var textView1: TextView
    lateinit var textView2: TextView

    private val messageReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_test_1).apply {
            setOnClickListener {
                startService(Intent(context, TestService1::class.java).apply {
                    putExtra(INT_VALUE, 1000)
                })
            }
        }

        findViewById<Button>(R.id.button_test_2).apply {
            setOnClickListener {
                startService(Intent(context, TestService2::class.java).apply {
                    putExtra(INT_VALUE, 1000)
                })
            }
        }

        textView1 = findViewById(R.id.text_view_1)
        textView2 = findViewById(R.id.text_view_2)

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter("MY_INTENT"))
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
        super.onDestroy()
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.apply {
                val textView = getStringExtra(STR_VALUE)
                val intValue1 = getIntExtra(INT_VALUE, Int.MIN_VALUE)
                val intValue2 = getLongExtra(INT_VALUE1, Long.MIN_VALUE)
                if (intValue1 >= 0 && intValue2 >= 0) when (textView) {
                    TEXT_VIEW_1 -> {
                        textView1.text = "Сумма чётных чисел в диапазоне 0..$intValue1 = $intValue2"
                    }
                    TEXT_VIEW_2 -> {
                        textView2.text = "Сумма нечётных чисел в диапазоне 0..$intValue1 = $intValue2"
                    }
                }
            }
        }
    }
}