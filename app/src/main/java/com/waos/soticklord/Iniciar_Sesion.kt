package com.waos.soticklord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import okhttp3.*
import java.io.IOException

class Iniciar_Sesion : AppCompatActivity() {

    private val client = OkHttpClient()
    private val supabaseUrl = "https://zropeiibzqefzjrkdzzp.supabase.co"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpyb3BlaWlienFlZnpqcmtkenpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkwMTc1NDYsImV4cCI6MjA3NDU5MzU0Nn0.ZJWqkOAbTul-RwIQrirajUSVdyI1w9Kh3kjek0vFMw8" // 👈 tu key pública de Supabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editNombre = findViewById<EditText>(R.id.edit_nombre)
        val editPassword = findViewById<EditText>(R.id.edit_password)
        val btnimagen = findViewById<ImageButton>(R.id.Iniciarwaza)

        val btnRegistrar = findViewById<Button>(R.id.Registrarse)


        btnimagen.setOnClickListener {
            val usuario = editNombre.text.toString()
            val password = editPassword.text.toString()
            validarUsuario(usuario, password)
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }



    }

    private fun validarUsuario(nombre: String, password: String) {
        if (nombre.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Consulta a la tabla "jugadores"
        val url =
            "$supabaseUrl/rest/v1/jugadores?nombre_usuario=eq.$nombre&contrasena=eq.$password"

        val request = Request.Builder()
            .url(url)
            .addHeader("apikey", apiKey)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@Iniciar_Sesion,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && body != null && body.contains("nombre_usuario")) {
                        // Login exitoso
                        val intent = Intent(this@Iniciar_Sesion, Perfil::class.java)
                        intent.putExtra("NOMBRE_USUARIO", nombre)
                        intent.putExtra("PASSWORD_USUARIO", password)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@Iniciar_Sesion,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}
