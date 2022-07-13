package ru.veider.myintentservice.testservice

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.veider.myintentservice.*
import ru.veider.myintentservice.service.MyIntentService


class TestService1 : MyIntentService("Тестовый сервис 1") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.apply {
            val intValue = getIntExtra(INT_VALUE, Int.MIN_VALUE)
            if (intValue > 0) {
                var sum: Long = 0
                for (i in 0..intValue) {
                    if (i % 2 == 0) sum += i
                    LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent(MY_INTENT).apply {
                        putExtra(STR_VALUE, TEXT_VIEW_1)
                        putExtra(INT_VALUE, i)
                        putExtra(INT_VALUE1, sum)
                    })
                    Thread.sleep(50)
                }
            }
        }
    }
}