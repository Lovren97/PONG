package hr.fer.ruazosa.pong

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        val layout = findViewById<ConstraintLayout>(R.id.myLayout)
        layout.setBackgroundColor(Color.BLACK)

        val singleplayer = findViewById<Button>(R.id.singleplayer)
        val mulitplayer = findViewById<Button>(R.id.multiplayer)
        val exit = findViewById<Button>(R.id.exit)

        singleplayer.setOnClickListener({view ->
            val intent = Intent(this,Local::class.java)
            startActivity(intent)
        })

        mulitplayer.setOnClickListener({view ->
            val intent = Intent(this,Online::class.java)
            startActivity(intent)
        })

        exit.setOnClickListener({view ->
            finish()
        })
    }
}
