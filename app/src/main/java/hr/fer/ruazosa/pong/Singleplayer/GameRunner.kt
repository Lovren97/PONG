package hr.fer.ruazosa.pong.Singleplayer

/**
 * Created by Ivan Lovrencic on 20.6.2018..
 */

class GameRunner : Thread{

    @Volatile private var running = true
    private var game: Game

    constructor(game: Game){
        this.game = game
    }

    override fun run() {
        var lastUpdate = System.currentTimeMillis()
        game.inti()

        while(running){

            var now = System.currentTimeMillis()
            var elapsed = now - lastUpdate
            lastUpdate = System.currentTimeMillis()

            if(elapsed < 100){
                game.update(elapsed)
                game.draw()
            }
        }
    }

    fun shutDown(){
        running = false
    }

}