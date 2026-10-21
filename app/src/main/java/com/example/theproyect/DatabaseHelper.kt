package com.example.theproyect

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "AlarmaReversa.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, pass TEXT)")
        db?.execSQL("CREATE TABLE Alertas (id INTEGER PRIMARY KEY AUTOINCREMENT, distancia INTEGER, fecha TEXT)")

        db?.execSQL("INSERT INTO Usuarios (user, pass) VALUES ('admin', '1234')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Usuarios")
        db?.execSQL("DROP TABLE IF EXISTS Alertas")
        onCreate(db)
    }

    fun validarLogin(usuario: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Usuarios WHERE user=? AND pass=?", arrayOf(usuario, contrasena))
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    // --- ESTA ES LA FUNCIÓN QUE TE FALTABA ---
    fun registrarUsuario(usuario: String, contrasena: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("user", usuario)
            put("pass", contrasena)
        }
        val resultado = db.insert("Usuarios", null, valores)
        return resultado != -1L
    }

    fun registrarAlerta(distancia: Int) {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("distancia", distancia)
        valores.put("fecha", java.util.Calendar.getInstance().time.toString())
        db.insert("Alertas", null, valores)
    }
}