package hr.fer.ruazosa.pong.Singleplayer

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import java.util.*

/**
 * Created by Ivan Lovrencic on 20.6.2018..
 */

class Game{
    lateinit var holder: SurfaceHolder
    lateinit var resource: Resources

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private lateinit var ball: Ball

    private var player: Bar? = null
    private var computer: Bar? = null

    private var scorePaint: Paint? = null

    constructor(holder: SurfaceHolder,resource: Resources,screenWidth: Int,screenHeigth: Int){
        this.holder = holder
        this.resource = resource
        this.screenWidth = screenWidth
        this.screenHeight = screenHeigth
    }

    fun inti() {
        // Will be called once per game, initialisation

        ball = Ball(screenWidth, screenHeight)
        ball.init()
        player = Bar(screenWidth, screenHeight, Bar.Position.LEFT)
        computer = Bar(screenWidth, screenHeight, Bar.Position.RIGHT)

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
        }
        if (ball.getPosition() === computer?.getPosition()) {
            player?.won()
            ball.init()
        }
        ball.update(elapsed)
        computer?.update(ball.getyPos())
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

    fun onTouchEventFirst(event: MotionEvent) {
        player?.setyPos(event.y.toInt())
        //inace za protiv kompa samo player?.setyPos(event.y.toInt())
    }

}