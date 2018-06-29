package hr.fer.ruazosa.pong

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import hr.fer.ruazosa.pong.Multiplayer.MultiplayerGameView
import hr.fer.ruazosa.pong.Multiplayer.UDPClient
import hr.fer.ruazosa.pong.Multiplayer.UDPServer
import hr.fer.ruazosa.pong.Singleplayer.GameView

class OnlineGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(MultiplayerGameView(this,intent.getStringExtra("IP")))

        OnlineService.activity = this
    }

    override fun onBackPressed() {
        OnlineService.client.sendMessage("END",OnlineService.ipAdress)
        finish()
    }
}
