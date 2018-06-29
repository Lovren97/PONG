package hr.fer.ruazosa.pong.Singleplayer

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by Ivan Lovrencic on 20.6.2018..
 */

class GameView : SurfaceView, SurfaceHolder.Callback{

    private var gameRunner: GameRunner? = null
    private lateinit var  game: Game

    constructor(context: Context) : super(context){

        val holder: SurfaceHolder = holder
        holder.addCallback(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {


        game?.onTouchEventFirst(event)
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {

        game = Game(holder,resources,width,height)
        gameRunner = GameRunner(game)
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