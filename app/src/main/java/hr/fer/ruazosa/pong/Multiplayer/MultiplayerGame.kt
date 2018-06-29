package hr.fer.ruazosa.pong.Multiplayer

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.SurfaceHolder
import hr.fer.ruazosa.pong.Online
import hr.fer.ruazosa.pong.OnlineService
import hr.fer.ruazosa.pong.Singleplayer.Ball
import hr.fer.ruazosa.pong.Singleplayer.Bar
import java.text.ParseException

/**
 * Created by Ivan Lovrencic on 24.6.2018..
 */

class MultiplayerGame{
    var holder: SurfaceHolder
    var resource: Resources

    private var server: UDPServer = UDPServer()
    private var client: UDPClient = UDPClient()

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    var ball: MultiplayerBall

     var player: Bar? = null
     var computer: Bar? = null

    private var scorePaint: Paint? = null
    private var ipAdress: String;

    constructor(holder: SurfaceHolder, resource: Resources, screenWidth: Int, screenHeigth: Int,ipAdress: String){
        this.ipAdress = ipAdress
        this.holder = holder
        this.resource = resource
        this.screenWidth = screenWidth
        this.screenHeight = screenHeigth
        this.ball = MultiplayerBall(this.screenWidth, screenHeight)
        OnlineService.ball = this.ball
    }

    fun inti() {
        // Will be called once per game, initialisation
        if(OnlineService.admin as Boolean){
            ball.init()
        }


        if(OnlineService.admin as Boolean){
            player = Bar(screenWidth, screenHeight, Bar.Position.LEFT)
            computer = Bar(screenWidth, screenHeight, Bar.Position.RIGHT)
        }
        else{
            player = Bar(screenWidth, screenHeight, Bar.Position.RIGHT)
            computer = Bar(screenWidth, screenHeight, Bar.Position.LEFT)
        }


        scorePaint = Paint()
        scorePaint?.setColor(Color.WHITE)
        scorePaint?.setTextAlign(Paint.Align.CENTER)
        scorePaint?.setAntiAlias(false)
        scorePaint?.setTextSize(26f)
        scorePaint?.setTypeface(Typeface.DEFAULT_BOLD)

    }

    fun update(elapsed: Long) {
        // elapsed is the time since this method was last called

        if (player?.getScreenRect()?.contains(ball.getScreenRect().left,
                ball.getScreenRect().centerY()) as Boolean) {
            ball.contact(player?.getPosition() as Bar.Position)
        }
        if (computer?.getScreenRect()?.contains(ball.getScreenRect().right,
                ball.getScreenRect().centerY()) as Boolean) {
            ball.contact(computer?.getPosition() as Bar.Position)
        }
        if (ball.getPosition() === player?.getPosition()) {
            computer?.won()
            ball.init()
            //OnlineService.client.sendMessage("C",OnlineService.ipAdress)
        }
        if (ball.getPosition() === computer?.getPosition()) {
            player?.won()
            ball.init()
            //OnlineService.client.sendMessage("Y",OnlineService.ipAdress)
        }
        ball.update(elapsed)
        //computer?.update(ball.getyPos())

        //var newScore = "Score"+","+player?.getScore().toString()+","+computer?.getScore().toString()
        //OnlineService.client.sendMessage(newScore,OnlineService.ipAdress)
    }

    fun drawScore(canvas: Canvas) {
        val builder = StringBuilder()
        builder.append(player?.getScore())
        builder.append(" : ")
        builder.append(computer?.getScore())

        val score = builder.toString()

        scorePaint?.textSize = 50.0f;

        canvas.drawText(score, (screenWidth / 2).toFloat(), 50f, scorePaint)

    }

    fun draw() {
        // Draw on Canvas

        val canvas = holder.lockCanvas()

        if (canvas != null) {
            // Colour the whole thing black
            canvas.drawColor(Color.BLACK)
            // Draw the ball at postiton x 50 y 50
            ball.draw(canvas)
            player?.draw(canvas)
            computer?.draw(canvas)
            drawScore(canvas)

            // Unlock the surface and post the updates
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun onTouchEventOpponent(position: String){
        var newPosition = Integer.parseInt(position)
        computer?.setyPos(newPosition)
    }

    fun onTouchEventFirst(event: MotionEvent) {
        var newPosition = "P"+","+event.y.toInt().toString()
        client.sendMessage(newPosition,ipAdress)
        player?.setyPos(event.y.toInt())

    }

}