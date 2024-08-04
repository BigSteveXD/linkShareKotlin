package com.example.linksharekotlinport

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.linksharekotlinport.ui.theme.LinkShareKotlinPortTheme
import java.io.PrintWriter
import java.net.Socket

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ipText = findViewById<EditText>(R.id.editText)
        val portText = findViewById<EditText>(R.id.editText2)
        val connectButton = findViewById<Button>(R.id.button)
        //val textView = findViewById<TextView>(R.id.textView)
        val textMessage = findViewById<EditText>(R.id.editText3)
        val sendButton = findViewById<Button>(R.id.button2)

        var soc: Socket? = null
        var peerWriter: PrintWriter? = null

        connectButton.setOnClickListener(){
            Thread{
                //Log.d("connectButton","ran")
                var peerIP = ipText.text.toString()
                var myPort = portText.text.toString()
                try{
                    //Log.d("connectButton", "connecting...")
                    soc = Socket(peerIP, myPort.toInt())
                    peerWriter = PrintWriter(soc!!.getOutputStream(), true)
                    peerWriter!!.println("CONNECTED")
                }catch(e: Exception){
                    println(e)
                    //Log.d("connectButton", "failed")
                }finally{
                    peerWriter?.close()
                    soc?.close()
                }
            }.start()
        }

        sendButton.setOnClickListener(){
            Thread{
                //Log.d("sendMessage", "ran")
                var text = textMessage.text
                try{
                    peerWriter?.println(text.toString())
                    //Log.d("sendMessage", "sent")
                }catch(e: Exception){
                    println(e)
                    //Log.d("sendMessage", "failed")
                }
            }.start()
        }
    }
}
