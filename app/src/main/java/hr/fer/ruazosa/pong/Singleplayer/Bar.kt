package hr.fer.ruazosa.pong.Singleplayer

import android.graphics.Canvas
import android.graphics.Rect

/**
 * Created by Ivan Lovrencic on 20.6.2018..
 */

class Bar : Sprite{

    enum class Position{
        LEFT,RIGHT
    }

    private var barWidth = 45
    private var barHeight = 226
    private var halfBarHeight = barHeight/2

    var accuracy = 0.08f

    private lateinit var position: Position

    private var score = 0

    private var yPos: Int = getScreenHeight()/2

    constructor(screeWidth: Int,screenHeight: Int,position: Position): super(screeWidth,screenHeight){
        this.position = position
        barWidth = ((screenHeight as Int)*0.035).toInt()
        barHeight = ((screenHeight as Int)* 0.15).toInt()
        halfBarHeight = (barHeight/1.2).toInt()
    }

    fun setScore(score: Int){
        this.score = score
    }

    fun getScreenRect() : Rect{
        var screenRect: Rect

        when(position){
            Position.LEFT -> screenRect = Rect(getMargin(),yPos-halfBarHeight,getMargin()+barWidth,yPos+halfBarHeight)
            Position.RIGHT -> screenRect = Rect(getScreenWidth() - barWidth - getMargin(),
        yPos - halfBarHeight, getScreenWidth() - getMargin(), yPos
                + halfBarHeight)
        }
        return  screenRect
    }

    fun getyPos(): Int {
        return yPos
    }

    fun setyPos(yPos:Int) {
        var help = yPos
        if (yPos < halfBarHeight)
        {
            help = halfBarHeight
        }
        else if (yPos > getScreenHeight() - halfBarHeight)
        {
            help = getScreenHeight() - halfBarHeight
        }
        this.yPos = help
    }

    fun update(yPosBall: Float) {
        var yPosBall = yPosBall

        if (yPosBall < halfBarHeight) {
            yPosBall = halfBarHeight.toFloat()
        } else if (yPosBall > getScreenHeight() - halfBarHeight) {
            yPosBall = (getScreenHeight() - halfBarHeight).toFloat()
        }

        val posDiff = yPosBall.toInt() - yPos
        yPos += (posDiff.toInt() * accuracy).toInt()

    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(getScreenRect(), paint)
    }
    fun getPosition():Position {
        return position
    }
    fun won() {
        score++
    }
    fun getScore():Int {
        return score
    }




}