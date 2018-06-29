package hr.fer.ruazosa.pong

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import hr.fer.ruazosa.pong.Singleplayer.GameView

class Local : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(GameView(this))

    }
}
