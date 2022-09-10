package com.example.dictea.helpers

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

class AudioHelper {
    companion object {
//        private val mediaPlayer : MediaPlayer by lazy { MediaPlayer.create() { setAudioAttributes(
//            AudioAttributes
//                .Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .build()) } }

        fun playAudio(context: Context, source : String) {

            try {
                val mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build())
                    setDataSource(source)
                    prepare()
                    start()
                }

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
            }
            catch (e: Exception) {
                Log.d("AudioHelper", e.toString())
            }
        }
    }
}