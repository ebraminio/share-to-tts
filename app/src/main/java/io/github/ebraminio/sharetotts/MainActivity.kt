package io.github.ebraminio.sharetotts

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.run {
            if (!action.equals(Intent.ACTION_SEND) || type?.startsWith("text/") != true)
                return

            getStringExtra(Intent.EXTRA_TEXT)?.let { text ->
                lateinit var tts: TextToSpeech
                val onInit = TextToSpeech.OnInitListener {
                    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onDone(p0: String?) = finish()
                        override fun onError(p0: String?) = Unit
                        override fun onStart(p0: String?) = Unit
                    })
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(text, 0, null, "")
                    } else {
                        tts.speak(text, 0, null)
                    }
                }
                tts = TextToSpeech(this@MainActivity, onInit)
            }
        }
    }
}
