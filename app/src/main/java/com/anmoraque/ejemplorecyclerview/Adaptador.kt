package com.anmoraque.ejemplorecyclerview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/*
 * Aqui se enlaza la informacion que tenemos y
 * hacer el bulding de las celdas con los elementos template
 * Recibe los elementos de la lista (platillos) y dos listener uno normal y otro largo
 */
class Adaptador(items: ArrayList<Platillo>, var listener: ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<ViewHolder>() {
    //Creamos la variable con los items y la inicializamos
    var items: ArrayList<Platillo>? = null
    //Creamos una variable para la funcion iniciarActionMode (para iniciar o no el actionMode)
    var multiSeleccion = false
    //Vamos a crear un nuevo Arraylist para guardar los indices de los elememntos seleccionados
    //(Los platillos seleccionados que quiero manipular)
    var itemsSeleccionados: ArrayList<Int>? = null
    //Creamos la variable ViewHolder
    var viewHolder: ViewHolder? = null
    //Inicializamos los Arraylist
    init {
        this.items = items
        itemsSeleccionados = ArrayList()
    }
    /*
     * Aqui vamos a crear nuestra vista asignandole el elemento que queremos renderizar
     * que en este caso es nuestro template_platillo y esta vista la añadimos al viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflamos la vista con el template
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.template_platillo, parent, false)
        //La añadimos al viewHolder
        viewHolder = ViewHolder(vista, listener, longClickListener)
        //Regresamos el viewHolder
        return viewHolder!!
    }
    /*
     * Aqui mapeamos los valores al item actual
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Creamos el valor para obtener el elemento actual
        val items = items?.get(position)
        //Le mapeamos los valores del ViewHolder
        holder.foto?.setImageResource(items?.foto!!)
        holder.nombre?.text = items?.nombre
        holder.precio?.text = items?.precio.toString() + "€"
        holder.rating?.rating = items?.rating!!
        //Validamos si itemsSeleccionados contiene la posicion
        //para ponerle un color distinto (se note la seleccion)
        if (itemsSeleccionados?.contains(position)!!){
            //El color lo cambiamos en la vista del holder
            holder.vista.setBackgroundResource(R.color.teal_700)
        }else{
            //Si no lo dejamos de color blanco (color de fabrica)
            holder.vista.setBackgroundResource(R.color.white)
        }
    }
    /*
     * Aqui se cuenta los items para ir cogiendolos
     */
    override fun getItemCount(): Int {
        return items?.count()!!
    }
    /*
     * Aqui creamos la funcion para iniciar el ActionMode y pasamos la variable a true
     */
    fun iniciarActionMode(){
        multiSeleccion = true
    }
    /*
     * Aqui creamos la funcion para destruir el ActionMode y pasamos la variable a false
     */
    fun destruirActionMode(){
        multiSeleccion = false
        //Limpiamos las referencias de los itemsSeleccionados
        itemsSeleccionados?.clear()
        //Y actualizo el adaptador
        notifyDataSetChanged()
    }
    /*
     * Aqui creamos la funcion para terminar el ActionMode y pasamos la variable a false
     */
    fun terminarActionMode(){
        //Hacemos un for para eliminar elementos seleccionados
        for (item in itemsSeleccionados!!){
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion = false
        //Y actualizo el adaptador
        notifyDataSetChanged()
    }
    /*
     * Con esta funcion decimos al adaptador que quiero seleccionar un elemento
     * Recibe un index
     */
    fun seleccionarItem(index: Int){
        //Validamos si multiSeleccion es verdadero para poder operar
        if (multiSeleccion){
            //Si el ArrayList itemsSeleccionados contiene el indice
            if (itemsSeleccionados?.contains(index)!!){
                //Eliminamos el elemento
                itemsSeleccionados?.remove(index)
            }else{
                //Y si no lo añadimos
                itemsSeleccionados?.add(index)
            }
            //Y actualizo el adaptador
            notifyDataSetChanged()
        }
    }
    /*
    * Con esta funcion sabemos el numero de elementos seleccionados
    * Regresa un entero y retorna el numero de items seleccionados
    */
    fun obtenerNumeroElementoSeleccionados(): Int{
        return itemsSeleccionados?.count()!!
    }
    /*
    * Con esta funcion eliminamos los elementos seleccionados
    * Regresa un entero y retorna el numero de items seleccionados
    */
    fun eliminarSeleccionados(){
        //Validamos si los items seleccionados es mayor a 0 (Sino no tiene sentido)
        if (itemsSeleccionados?.count()!! > 0){
            //Creamos una variable de ArrayList para los elementos seleccionados que voy a eliminar
            var itemsEliminados = ArrayList<Platillo>()
            //Hacemos un for ((Recorrer todos los elementos) para cada index del ArrayList itemsSeleccionados
            for(index in itemsSeleccionados!!){
                //Añadimos al ArrayList itemsEliminados los elementos (items) seleccionados de ese index
                itemsEliminados.add(items?.get(index)!!)
            }
            //Ahora eliminamos todos esos elementos del ArrayList itemsEliminados
            items?.removeAll(itemsEliminados)
            //Y por ultimos limpiamos el ArrayList itemsSeleccionados
            itemsSeleccionados?.clear()
        }
    }
}