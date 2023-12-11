package com.example.myica.model.service

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.myica.R
import kotlin.random.Random

class PlanNotificationService(
    private val context:Context
){
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun planCreateNotification(){
        val notification=NotificationCompat.Builder(context,"plan_notification")
            .setContentTitle("Plan Reminder")
            .setContentText("Plan has been created.")
            .setSmallIcon(R.drawable.baseline_checklist_24)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun planEditNotification(){
        val notification=NotificationCompat.Builder(context,"plan_notification")
            .setContentTitle("Plan Reminder")
            .setContentText("Plan has been edited.")
            .setSmallIcon(R.drawable.baseline_checklist_24)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }


    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}