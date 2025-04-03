package com.pmdm.casino.ui.features.musicaFondo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.pmdm.casino.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MusicaViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.music}")
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release() // Libera memoria cuando ya no se usa
    }
}