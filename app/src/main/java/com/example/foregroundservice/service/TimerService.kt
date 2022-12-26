package com.example.foregroundservice.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.foregroundservice.MainActivity
import com.example.foregroundservice.R
import com.example.foregroundservice.helpers.AppConstants
import com.example.foregroundservice.helpers.AppConstants.CHANNEL_NAME
import com.example.foregroundservice.helpers.AppConstants.EMPTY_STRING
import com.example.foregroundservice.helpers.AppConstants.EXTRA_NAME
import com.example.foregroundservice.helpers.AppConstants.SERVICE_ID

class TimerService : Service() {

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			createNotificationChanel(
				getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			)
		}

		startForeground(
			SERVICE_ID, getNotification(
				pendingIntent = getPendingIntent(),
				contentText = intent?.getStringExtra(EXTRA_NAME) ?: EMPTY_STRING
			)
		)
		return START_NOT_STICKY
	}

	override fun onBind(intent: Intent): IBinder? {
		return null
	}

	private fun getPendingIntent(): PendingIntent {
		return PendingIntent.getActivity(
			this,
			0,
			Intent(this, MainActivity::class.java),
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
			} else {
				PendingIntent.FLAG_CANCEL_CURRENT
			}
		)
	}

	private fun getNotification(pendingIntent: PendingIntent, contentText: String): Notification {
		return NotificationCompat.Builder(this, AppConstants.CHANNEL_ID)
			.setContentTitle("Example of the foreground service")
			.setContentText("The name of the person is $contentText")
			.setSmallIcon(R.drawable.ic_android)
			.setOngoing(true)
			.setAutoCancel(false)
			.setContentIntent(pendingIntent)
			.build()
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createNotificationChanel(notificationManager: NotificationManager) {
		notificationManager.createNotificationChannel(NotificationChannel(
			AppConstants.CHANNEL_ID,
			CHANNEL_NAME,
			NotificationManager.IMPORTANCE_LOW
		))
	}

}