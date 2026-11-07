
package com.fisch.autofisher

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent

class FishingService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var fishing = false
    private var controls: FloatingControls? = null

    private val fishingLoop = object : Runnable {
        override fun run() {
            if (!fishing) return

            val x = resources.displayMetrics.widthPixels / 2f
            val y = resources.displayMetrics.heightPixels / 2f

            val path = Path().apply {
                moveTo(x, y)
            }

            val gesture = GestureDescription.Builder()
                .addStroke(
                    GestureDescription.StrokeDescription(
                        path, 0, 100
                    )
                )
                .build()

            dispatchGesture(gesture, null, null)
            handler.postDelayed(this, 2000)
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        if (Settings.canDrawOverlays(this)) {
            controls = FloatingControls(
                this,
                onStart = { startFishing() },
                onStop = { stopFishing() }
            )
            controls?.show()
        }
    }

    fun startFishing() {
        if (fishing) return
        fishing = true
        handler.post(fishingLoop)
    }

    fun stopFishing() {
        fishing = false
        handler.removeCallbacks(fishingLoop)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {
        stopFishing()
    }

    override fun onDestroy() {
        stopFishing()
        controls?.hide()
        controls = null
        super.onDestroy()
    }
}
