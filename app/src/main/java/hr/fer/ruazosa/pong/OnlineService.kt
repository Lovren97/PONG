package hr.fer.ruazosa.pong

import android.annotation.SuppressLint
import android.app.Activity
import hr.fer.ruazosa.pong.Multiplayer.MultiplayerBall
import hr.fer.ruazosa.pong.Multiplayer.MultiplayerGame
import hr.fer.ruazosa.pong.Multiplayer.UDPClient
import hr.fer.ruazosa.pong.Multiplayer.UDPServer

@SuppressLint("StaticFieldLeak")
/**
 * Created by Ivan Lovrencic on 24.6.2018..
 */

object OnlineService{

    lateinit var server: UDPServer
    lateinit var client: UDPClient
    lateinit var game: MultiplayerGame
    lateinit var ipAdress: String
    lateinit var ball: MultiplayerBall
    var admin: Boolean? = null
    lateinit var activity: Activity
    var dX: Float? = null
    var dY: Float? = null
    lateinit var difference: String
}