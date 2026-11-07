
package com.fisch.autofisher

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout

class FloatingControls(
    private val context: Context,
    private val onStart: () -> Unit,
    private val onStop: () -> Unit
) {
    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var panel: LinearLayout? = null

    fun show() {
        if (panel != null) return

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(12, 12, 12, 12)
            setBackgroundColor(0xFF222222.toInt())

            addView(Button(context).apply {
                text = "START FISHING"
                setOnClickListener { onStart() }
            })

            addView(Button(context).apply {
                text = "STOP FISHING"
                setOnClickListener { onStop() }
            })
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 30
            y = 150
        }

        windowManager.addView(layout, params)
        panel = layout
    }

    fun hide() {
        panel?.let {
            windowManager.removeView(it)
            panel = null
        }
    }
}
