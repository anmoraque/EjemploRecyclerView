package com.anmoraque.ejemplorecyclerview
/*
 * Modelo de datos (en este caso de platillos)
 */
class Platillo(nombre: String, precio: Double, rating: Float, foto: Int) {
    var nombre = ""
    var precio = 0.0
    var rating = 0.0F
    var foto = 0

    init {
        this.nombre = nombre
        this.precio = precio
        this.rating = rating
        this.foto = foto
    }
}