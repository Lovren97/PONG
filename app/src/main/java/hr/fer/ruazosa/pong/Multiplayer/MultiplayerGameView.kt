package hr.fer.ruazosa.pong.Multiplayer

import android.content.Context
import android.view.Display
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import hr.fer.ruazosa.pong.OnlineService
import hr.fer.ruazosa.pong.Singleplayer.Game
import hr.fer.ruazosa.pong.Singleplayer.GameRunner

/**
 * Created by Ivan Lovrencic on 24.6.2018..
 */

class MultiplayerGameView : SurfaceView, SurfaceHolder.Callback{

    private var gameRunner: OnlineRunner? = null
    private var ipAdress: String;

    constructor(context: Context,ipAdress: String) : super(context){

        this.ipAdress = ipAdress
        OnlineService.ipAdress = ipAdress
        val holder: SurfaceHolder = holder
        holder.addCallback(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        OnlineService.game?.onTouchEventFirst(event)
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        OnlineService.game = MultiplayerGame(holder,resources,width,height,ipAdress)
        gameRunner = OnlineRunner(OnlineService.game)
        gameRunner?.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        if(gameRunner != null){
            gameRunner?.shutDown()
        }

        while (gameRunner != null){
            try {
                gameRunner?.join()
                gameRunner = null
            }catch (e: InterruptedException){
                e.printStackTrace()
            }
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        // nothing
    }

}