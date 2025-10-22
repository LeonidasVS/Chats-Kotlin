package com.example.chats_kotlin

import java.util.Calendar
import java.util.Locale

object Constants {
    fun obtenerTiempo(): Long {
            return System.currentTimeMillis()
    }

    fun formatoFecha(tiempo: Long): String{
        val calendario=Calendar.getInstance(Locale.ENGLISH)
        calendario.timeInMillis=tiempo
        return android.text.format.DateFormat.format("dd/MM/yyy", calendario).toString()
    }
}

