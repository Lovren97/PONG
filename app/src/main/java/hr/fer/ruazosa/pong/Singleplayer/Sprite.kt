package hr.fer.ruazosa.pong.Singleplayer

import android.graphics.Color
import android.graphics.Paint

/**
 * Created by Ivan Lovrencic on 20.6.2018..
 */

open class Sprite{

    private var screenWidth = 0
    private var screenHeight = 0

    private var margin = 200

    var paint: Paint

    constructor(screenWidth: Int,screenHeight: Int){
        this.screenHeight = screenHeight
        this.screenWidth = screenWidth

        margin = (((screenWidth as Int)*0.1)).toInt()

        paint = Paint()
        paint.color = Color.WHITE
    }

    fun getScreenWidth() : Int{
        return screenWidth
    }

    fun getScreenHeight() : Int{
        return screenHeight
    }

    fun getMargin(): Int{
        return margin
    }



}