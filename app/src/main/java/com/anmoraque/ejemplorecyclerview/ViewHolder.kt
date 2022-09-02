package com.anmoraque.ejemplorecyclerview

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * Aqui se declaran los objetos que vamos a asociar con nuestro modelo de datos
 * Y reusamos las vistas que creamos para no tener que crearlas cada vez que se renderizan
 */
class ViewHolder(vista: View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener, View.OnLongClickListener {
    //Inicializamos la variable vista
    var vista = vista
    //Creamos las demas variables y las inicializamos
    var foto: ImageView? = null
    var nombre: TextView? = null
    var precio: TextView? = null
    var rating: RatingBar? = null
    //Creamos un elemento para el listener de la interfaz ClickListener
    var listener: ClickListener? = null
    //Creamos un elemento para el Longlistener de la interfaz LongClickListener
    var longClickListener: LongClickListener? = null
    init {
        foto = vista.findViewById(R.id.iFoto)
        nombre = vista.findViewById(R.id.tvNombrePlato)
        precio = vista.findViewById(R.id.tvPrecio)
        rating = vista.findViewById(R.id.ratingBar)
        //Inicializamos el elemento listener
        this.listener = listener
        //Inicializamos el elemento Longlistener
        this.longClickListener = longClickListener
        //Mandamos a escuchar la vista listener
        vista.setOnClickListener(this)
        //Mandamos a escuchar la vista longlistener
        vista.setOnLongClickListener(this)
    }
    /*
     * Implementamos el Onclick al ViewHolder
     */
    override fun onClick(p0: View?) {
        //Dentro de esta funcion sobrescrita del onclick llamamos al elemento listener
        //el cual hace referencia a la interfaz ClickListener
        //Le pasamos la posicion de la vista y la del adapter
        this.listener?.onClick(p0!!, adapterPosition)
    }
    /*
     * Implementamos el onLongClick al ViewHolder
     */
    override fun onLongClick(p0: View?): Boolean {
        //Dentro de esta funcion sobrescrita del onLongClick llamamos al elemento longClicklistener
        //el cual hace referencia a la interfaz LongClickListener
        //Le pasamos la posicion de la vista y la del adapter
        this.longClickListener?.LongClick (p0!!, adapterPosition)
        //Requiere un return true
        return true
    }
}