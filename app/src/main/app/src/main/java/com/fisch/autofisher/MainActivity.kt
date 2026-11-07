
package com.fisch.autofisher

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
        }

        layout.addView(TextView(this).apply {
            text = "FISCH AUTO FISHER"
            textSize = 26f
        })

        layout.addView(Button(this).apply {
            text = "Enable Accessibility Service"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        })

        layout.addView(Button(this).apply {
            text = "Start Floating Controls"
            setOnClickListener {
                startService(Intent(this@MainActivity, FishingService::class.java))
            }
        })

        setContentView(layout)
    }
}
