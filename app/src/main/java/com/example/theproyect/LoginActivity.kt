package com.example.theproyect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)
        etUser = findViewById(R.id.etUser)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnLogin.setOnClickListener {
            val usuario = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()

            if (usuario.isNotEmpty() && pass.isNotEmpty()) {
                val esValido = dbHelper.validarLogin(usuario, pass)
                if (esValido) {
                    Toast.makeText(this, "¡Bienvenido, $usuario!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegistrar.setOnClickListener {
            val usuario = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()

            if (usuario.isNotEmpty() && pass.isNotEmpty()) {
                val creadoExitoso = dbHelper.registrarUsuario(usuario, pass)
                if (creadoExitoso) {
                    Toast.makeText(this, "¡Cuenta creada con éxito! Ahora inicia sesión", Toast.LENGTH_SHORT).show()
                    etUser.text.clear()
                    etPass.text.clear()
                } else {
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Escribe un usuario y contraseña para registrarte", Toast.LENGTH_SHORT).show()
            }
        }
    }
}