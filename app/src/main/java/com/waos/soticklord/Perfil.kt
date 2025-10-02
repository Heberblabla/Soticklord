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
            // Aquí puedes hacer una consulta a tu BD o API con el idJugador
            // y llenar los TextView con la info del jugador (nombre, monedas, tropas, etc.)
            // Ejemplo ficticio:
            println("ID del jugador recibido: $idJugador")
        } else {
            println("Error: no se recibió el ID del jugador")
        }
    }

    fun entrar(view: View) {
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
    }
    private fun obtenerDatosJugador(idJugador: Int, callback: (Int, Int, Int) -> Unit) {
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
