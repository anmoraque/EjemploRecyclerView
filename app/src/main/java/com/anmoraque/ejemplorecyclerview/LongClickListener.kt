package com.anmoraque.ejemplorecyclerview

import android.view.View

/*
 * Creamos una interfaz para el evento listener largo, las interfaces nos sirve como un puente
 * para poder implementar funciones o metodos cuando ocurre un cierto evento
 */
interface LongClickListener {
    //Declaramos una funcion dentro para escuchar
    //Requiere de una vista y de una posicion
    fun LongClick (vista: View, index: Int){

    }
}