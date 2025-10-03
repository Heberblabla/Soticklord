package com.waos.soticklord

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.waos.soticklord.Iniciar_Sesion
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class Perfil : AppCompatActivity() {
    private val client = OkHttpClient()
    private val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8" //  key pública
    var misMonedas = 0
    var miExperiencia = 0
    var misMedallas = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Ocultar barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Ajuste de paddings por barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔑 Recibir el ID del jugador
        val idJugador = intent.getIntExtra("ID_JUGADOR", -1)

        if (idJugador != -1) {
            val numero = idJugador.toInt()

            // aquí llamas la función y usas el callback
            obtenerDatosJugador(numero) { monedas, experiencia, medallas ->

                // Aquí ya puedes asignar a tus variables
                misMonedas = monedas
                miExperiencia = experiencia
                misMedallas = medallas
                asignar_datos_principales()
            }


        } else {
            println("Error: no se recibió    el ID del jugador")
        }

        asignar_datos_principales()
    }

    private fun asignar_datos_principales(){
        val Nivel = findViewById<TextView>(R.id.Nivel_General)
        val nuevo =  calcularNivel(miExperiencia)
        Nivel.text = nuevo.toString()
        val monedas = findViewById<TextView>(R.id.Monedas)
        monedas.text = misMonedas.toString()
        val Medallas = findViewById<TextView>(R.id.Medallas)
        Medallas.text = misMedallas.toString()
    }

    private fun calcularNivel(experiencia: Int): Int {
        var expRestante = experiencia
        var nivel = 1
        var expNecesaria = 1000

        while (expRestante >= expNecesaria) {
            expRestante -= expNecesaria
            nivel++

            // a partir del nivel 2 se aumenta 25%
            expNecesaria = (expNecesaria * 1.25).toInt()
        }

        return nivel
    }

    fun entrar(view: View) {
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
    }

    fun obtenerDatosJugador(idJugador: Int, callback: (Int, Int, Int) -> Unit) {
        val url = "$supabaseUrl/rest/v1/jugadores?id_jugador=eq.$idJugador"

        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        println("Error en la respuesta: ${it.code}")
                        return
                    }

                    val body = it.body?.string()
                    if (body != null) {
                        val jsonArray = JSONArray(body)
                        if (jsonArray.length() > 0) {
                            val jugador = jsonArray.getJSONObject(0)

                            val monedas = jugador.getInt("monedas")
                            val experiencia = jugador.getInt("experiencia")
                            val medallas = jugador.getInt("medallas")

                            // Devuelves los datos al callback
                            callback(monedas, experiencia, medallas)

                        }
                    }
                }
            }
        })
    }

}
