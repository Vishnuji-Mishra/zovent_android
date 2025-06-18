package com.app.zovent.ui.main.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.app.zovent.R
import com.app.zovent.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        enableEdgeToEdge()
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val uri = Uri.parse("android.resource://$packageName/${R.raw.splash_video}")
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : androidx.media3.common.Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == ExoPlayer.STATE_ENDED) {
                    startActivity(Intent(this@SplashActivity, SignupFlowActivity::class.java))
                    finish()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

}