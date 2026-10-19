package com.example.a24012021002_mad_p6

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var ivAlarmClock: ImageView
    private lateinit var ivHeart: ImageView
    private lateinit var btnCreateAlarm: Button
    private lateinit var btnCancelAlarm: Button

    private var alarmAnimationDrawable: AnimationDrawable? = null
    private var isHeartAnimating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable Edge-to-Edge display
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle Window Insets for proper status and navigation bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI Views
        ivAlarmClock = findViewById(R.id.ivAlarmClock)
        ivHeart = findViewById(R.id.ivHeart)
        btnCreateAlarm = findViewById(R.id.btnCreateAlarm)
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm)

        // Get AnimationDrawable instance from alarm image
        alarmAnimationDrawable = ivAlarmClock.drawable as? AnimationDrawable

        // Button Click to Start Frame-by-Frame Alarm Animation
        btnCreateAlarm.setOnClickListener {
            alarmAnimationDrawable?.start()
            startHeartAnimation()
            Toast.makeText(this, "Alarm Created & Animation Started", Toast.LENGTH_SHORT).show()
        }

        // Button Click to Stop Alarm Animation
        btnCancelAlarm.setOnClickListener {
            alarmAnimationDrawable?.stop()
            stopHeartAnimation()
            Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
        }

        // Toggle Heart Animation on Heart icon click
        ivHeart.setOnClickListener {
            if (isHeartAnimating) {
                stopHeartAnimation()
                Toast.makeText(this, "Heart Animation Stopped", Toast.LENGTH_SHORT).show()
            } else {
                startHeartAnimation()
                Toast.makeText(this, "Heart Animation Started", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startHeartAnimation() {
        ivHeart.setImageResource(R.drawable.heart_animation_list)
        val heartAnim = ivHeart.drawable as? AnimationDrawable
        heartAnim?.start()
        isHeartAnimating = true
    }

    private fun stopHeartAnimation() {
        val heartAnim = ivHeart.drawable as? AnimationDrawable
        heartAnim?.stop()
        ivHeart.setImageResource(R.drawable.ic_heart_outline)
        isHeartAnimating = false
    }

    /**
     * Called when the current Window of the activity gains or loses focus.
     * AnimationDrawable cannot be started in onCreate() because views are not attached yet.
     * onWindowFocusChanged() is the ideal lifecycle callback to start frame animations.
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            alarmAnimationDrawable?.start()
        }
    }
}
