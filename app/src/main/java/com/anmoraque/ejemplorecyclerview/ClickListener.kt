package com.anmoraque.ejemplorecyclerview

import android.view.View

/*
 * Creamos una interfaz para el evento listener, las interfaces nos sirve como un puente
 * para poder implementar funciones o metodos cuando ocurre un cierto evento
 */
interface ClickListener {
    //Declaramos una funcion dentro para escuchar
    //Requiere de una vista y de una posicion
    fun onClick(vista: View, index: Int){

    }
}