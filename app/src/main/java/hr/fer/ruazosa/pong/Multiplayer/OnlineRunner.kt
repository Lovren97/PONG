package hr.fer.ruazosa.pong.Multiplayer

import hr.fer.ruazosa.pong.OnlineService
import hr.fer.ruazosa.pong.Singleplayer.Game

/**
 * Created by Ivan Lovrencic on 24.6.2018..
 */

class OnlineRunner : Thread{

    @Volatile private var running = true
    private var game: MultiplayerGame

    constructor(game: MultiplayerGame){
        this.game = game
    }

    override fun run() {
        var lastUpdate = System.currentTimeMillis()
        game.inti()

        while(running){

            var now = System.currentTimeMillis()
            var elapsed = now - lastUpdate
            lastUpdate = System.currentTimeMillis()

            if(elapsed < 25){
                if(OnlineService.admin as Boolean){
                    game.update(elapsed)
                    game.draw()
                }else{
                    game.draw()
                }
            }
        }
    }

    fun shutDown(){
        running = false
    }

}