package com.app.zovent.ui.main.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.app.zovent.R
import com.app.zovent.databinding.ActivitySplashBinding
import com.app.zovent.utils.PreferenceEntity.IS_LOGIN
import com.app.zovent.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.net.toUri
import com.app.zovent.utils.PreferenceEntity.TOKEN

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        enableEdgeToEdge()
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val uri = "android.resource://$packageName/${R.raw.splash_video}".toUri()
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : androidx.media3.common.Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == ExoPlayer.STATE_ENDED) {
                    Log.i("TAG", "onPlaybackStateChanged: "+Preferences.getStringPreference(this@SplashActivity, IS_LOGIN))
                    Log.i("TAG", "onPlaybackStateChanged: "+Preferences.getStringPreference(this@SplashActivity, TOKEN))
                    if (Preferences.getStringPreference(this@SplashActivity, IS_LOGIN)=="2"){
                        startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                        finish()
                    }else {
                        startActivity(Intent(this@SplashActivity, SignupFlowActivity::class.java))
                        finish()
                    }

                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

}