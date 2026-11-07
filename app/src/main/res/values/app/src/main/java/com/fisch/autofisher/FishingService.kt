
package com.fisch.autofisher

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent

class FishingService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var fishing = false

    private val fishingLoop = object : Runnable {
        override fun run() {
            if (!fishing) return

            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels

            val path = Path().apply {
                moveTo(screenWidth / 2f, screenHeight / 2f)
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

    fun startFishing() {
        if (fishing) return
        fishing = true
        handler.post(fishingLoop)
    }

    fun stopFishing() {
        fishing = false
        handler.removeCallbacks(fishingLoop)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {
        stopFishing()
    }

    override fun onDestroy() {
        stopFishing()
        super.onDestroy()
    }
}
