package hr.fer.ruazosa.pong

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.net.Proxy.getPort
import android.net.wifi.WifiManager
import android.os.Handler
import android.text.format.Formatter
import android.util.Log
import android.view.Display
import android.widget.EditText
import android.widget.Toast
import hr.fer.ruazosa.pong.Multiplayer.UDPClient
import hr.fer.ruazosa.pong.Multiplayer.UDPServer
import kotlinx.android.synthetic.main.activity_online.*
import java.io.IOException
import java.lang.Thread.sleep
import java.lang.reflect.Array.getLength
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.regex.Pattern


class Online : AppCompatActivity() {

    private var myIp: String? = null
    protected lateinit var server: UDPServer
    protected lateinit var client: UDPClient
    private lateinit var ipAdresa: Pattern
    lateinit var start: Button
    internal val handler = Handler()
    lateinit var newText: TextView
    lateinit var connect: Button
    var connected = false
    lateinit var opponentsIp: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_online)

        val layout = findViewById<ConstraintLayout>(R.id.niceLayout)
        layout.setBackgroundColor(Color.BLACK)


        server = UDPServer()
        client = UDPClient()

        OnlineService.client = client
        OnlineService.server = server
        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        myIp = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)

        server.startServerSocket(this@Online,this)
        val ipAdress = findViewById<TextView>(R.id.ipAdress)
        ipAdress.setText(myIp)

        val enterIp = findViewById<EditText>(R.id.player2ip)
        connect = findViewById<Button>(R.id.connect)

        newText = findViewById<TextView>(R.id.textView)

        ipAdresa = Pattern.compile("\\d+.\\d+.\\d+.\\d+.")

        val stop = findViewById<Button>(R.id.stop)
        stop.isEnabled = false

        stop.setOnClickListener({view ->
            connect.isEnabled = true
            updateUI("Stopped!")
            client.sendMessage("STOP",OnlineService.ipAdress)
            connected = false
        })

        connect.setOnClickListener({view ->
            var player2 = enterIp.text.toString()
            if(ipAdresa.matcher(player2).matches()){
                opponentsIp = player2
                OnlineService.ipAdress = player2
                client.sendMessage("Connected",player2)
                updateUI("Connecting...")

                connect.isEnabled = false
                var string: String;

                if(connected){
                    startGame()
                }
                else{
                    stop.isEnabled = true
                    Log.d("Ipak sam tu!","Kaj ima? ")
                    connected = true
                }
            }
            else{
                Toast.makeText(this,"Invalid ip adress!",Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        server.stopThread()
        finish()
    }

   fun updateUI(stringData: String) {

        handler.post(Runnable {
            val s = newText.getText().toString()
            if (stringData.trim { it <= ' ' }.length != 0)
                newText.setText(s + "\n" + stringData)
        })
    }

    fun startGame(){
        //server.stopThread()
        setAdmin()
        updateUI("Connected!")
        val intent = Intent(this,OnlineGame::class.java)
        intent.putExtra("IP",opponentsIp)
        startActivity(intent)
        finish()
    }

    fun setAdmin(){
        var myIpNumbers = myIp?.split(".")
        var opponentIpNumbers = opponentsIp?.split(".")

        var myNumber = myIpNumbers!![3]?.toInt()
        var opponentNumber = opponentIpNumbers!![3].toInt()

        OnlineService.admin = myNumber >= opponentNumber
    }
}
