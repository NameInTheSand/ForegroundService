package com.example.foregroundservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.foregroundservice.helpers.AppConstants.EXTRA_NAME
import com.example.foregroundservice.service.TimerService

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initListeners()
	}

	private fun initListeners() {
		findViewById<Button>(R.id.btn_start_service).setOnClickListener {
			startService(
				Intent(this, TimerService::class.java).also {
					it.putExtra(EXTRA_NAME, findViewById<EditText>(
						R.id.et_name_input
					).text.toString())
				}
			)
		}

		findViewById<Button>(R.id.btn_stop_service).setOnClickListener {
			stopService(Intent(this, TimerService::class.java))
		}
	}
}