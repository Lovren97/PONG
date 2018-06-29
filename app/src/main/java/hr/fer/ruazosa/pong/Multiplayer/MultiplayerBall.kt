package hr.fer.ruazosa.pong.Multiplayer

import android.graphics.Canvas
import android.graphics.Rect
import hr.fer.ruazosa.pong.Online
import hr.fer.ruazosa.pong.OnlineService
import hr.fer.ruazosa.pong.Singleplayer.Bar
import hr.fer.ruazosa.pong.Singleplayer.Sprite
import java.util.*

/**
 * Created by Ivan Lovrencic on 24.6.2018..
 */

class MultiplayerBall: Sprite {

    private var xPos: Float = (getScreenWidth() / 2).toFloat()
    private var yPos = 50f

    private var velocity_x = 0.7f
    private var velocity_y = 0.7f

    var xDirection = 1
    var yDirection = 1

    private var position: Bar.Position? = null

    private var ballSize = 45

    constructor(screenWidth: Int,screenHeight: Int): super(screenWidth,screenHeight){
        ballSize = (screenHeight.toInt() * 0.03).toInt()

        velocity_x = (screenWidth * 0.000364583).toFloat()
        velocity_y = (screenWidth * 0.000364583).toFloat()

    }

    fun init() {
        val random = Random()

        yPos =  340.0f  // random.nextInt(getScreenHeight()).toFloat()
        xPos =  600.0f  //(getScreenWidth() / 2).toFloat()

        xDirection = randomDirection()
        yDirection = randomDirection()

        position = null
    }

    fun getScreenRect(): Rect {
        return Rect(xPos.toInt(), yPos.toInt(), xPos.toInt() + ballSize, yPos.toInt() + ballSize)
    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(getScreenRect(), paint)
    }

    fun update(elapsed: Long) {

        if (getScreenRect().left - getMargin() <= 0) {

            position = Bar.Position.LEFT

        } else if (getScreenRect().right + getMargin() >= getScreenWidth()) {

            position = Bar.Position.RIGHT
        }

        if (getScreenRect().top <= 0) {
            yDirection = 1
        } else if (getScreenRect().bottom >= getScreenHeight()) {
            yDirection = -1
        }

        // Calculating new position
        xPos += elapsed.toFloat() * velocity_x * xDirection.toFloat()
        yPos += elapsed.toFloat() * velocity_y * yDirection.toFloat()

        var newPosition: String = "B"+","+xPos.toString()+","+yPos.toString()
        if(position == Bar.Position.LEFT){
            newPosition = newPosition+","+"L"
        }else{
            newPosition = newPosition+","+"R"
        }
        newPosition = newPosition + "," + OnlineService.game.player?.getScore().toString() + "," + OnlineService.game.computer?.getScore().toString()
        OnlineService.client.sendMessage(newPosition, OnlineService.ipAdress)

    }

    fun newPosition(xPos: Float,yPos: Float,position: Bar.Position){
        this.xPos = xPos
        this.yPos = yPos
        this.position = position
    }

    private fun randomDirection(): Int {

        val random = Random()

        var number = random.nextInt(2)
        when (number) {
            1 -> number = 1
            else -> number = -1
        }
        return number
    }

    fun getyPos(): Float {
        return yPos
    }

    fun contact(pos: Bar.Position) {

        when (pos) {
            Bar.Position.LEFT -> xDirection = 1
            Bar.Position.RIGHT -> xDirection = -1
        }
    }

    fun getPosition(): Bar.Position? {
        return position
    }
}