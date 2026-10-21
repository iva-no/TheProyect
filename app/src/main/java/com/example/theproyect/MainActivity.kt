package com.example.theproyect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvDistancia: TextView
    private lateinit var tvEstado: TextView
    private lateinit var imgAlerta: ImageView

    private lateinit var etSimulador: EditText
    private lateinit var btnSimular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        tvDistancia = findViewById(R.id.tvDistancia)
        tvEstado = findViewById(R.id.tvEstado)
        imgAlerta = findViewById(R.id.imgAlerta)

        etSimulador = findViewById(R.id.etSimulador)
        btnSimular = findViewById(R.id.btnSimular)

        btnSimular.setOnClickListener {
            val distanciaTexto = etSimulador.text.toString()

            if (distanciaTexto.isNotBlank()) {
                val distanciaCm = distanciaTexto.trim().toIntOrNull() ?: 0

                procesarDistanciaRecibida(distanciaCm)

                etSimulador.text.clear()
            } else {
                Toast.makeText(this, "Por favor ingresa un número", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun procesarDistanciaRecibida(distanciaCm: Int) {
        tvDistancia.text = "Distancia: $distanciaCm cm"

        if (distanciaCm > 25) {
            tvEstado.text = "Zona Segura - OK"
            tvEstado.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        } else if (distanciaCm in 11..25) {
            tvEstado.text = "Advertencia - Precaución"
            tvEstado.setTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark))
        } else if (distanciaCm <= 10) {
            tvEstado.text = "¡PELIGRO! Deténgase"
            tvEstado.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            dbHelper.registrarAlerta(distanciaCm)
        }
    }
}