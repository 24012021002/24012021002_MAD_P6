package com.example.a24012021002_mad_p6

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    private lateinit var ivSplashLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivSplashLogo = findViewById(R.id.ivSplashLogo)

        // Load Twin Animation using AnimationUtils class and loadAnimation() method
        val twinAnimation = AnimationUtils.loadAnimation(this, R.anim.twinanimation)

        // Set Animation Listener using setAnimationListener() method
        twinAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Short hold before transitioning to MainActivity
                ivSplashLogo.postDelayed({
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    // Use overridePendingTransition() method for smooth activity transition
                    @Suppress("DEPRECATION")
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    // Finish SplashActivity so user cannot return to it by pressing Back
                    finish()
                }, 600)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Start twin animation on the UVPCE logo
        ivSplashLogo.startAnimation(twinAnimation)
    }

    // Demonstrate onWindowFocusChanged() method to start AnimationDrawable
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val animationDrawable = ivSplashLogo.drawable as? AnimationDrawable
            animationDrawable?.start()
        }
    }
}
