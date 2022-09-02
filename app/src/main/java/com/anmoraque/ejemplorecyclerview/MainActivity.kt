package com.anmoraque.ejemplorecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/*
 * En este ejemplo vamos a ver como implementar un RecyclerView
 * Como hacerle el listener normal y el largo (Dejando pulsado)
 * SwipeRefreshLayout (Reactualizar nuestros datos)
 * ActionMode (interfaz de usuario de modo contextual) podemos definir inicio y final
 */
class MainActivity : AppCompatActivity() {
    //Creamos las variables que necesitamos como siempre
    var lista: RecyclerView? = null
    var adaptador: Adaptador? = null
    //En esta variable administramos como se muestra el template_platillo
    var layoutManager: RecyclerView.LayoutManager? = null
    //Variable para el actionmode para saber si estamos dentro del Actionmode
    var isActionMode = false
    //Creamos un nuevo objeto para el actionMode
    var actionMode: androidx.appcompat.view.ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Creamos el ArrayList para los platillos
        val platillos = ArrayList<Platillo>()
        //Añadimos platillo al ArrayList
        platillos.add(Platillo("Platillo1", 250.0, 3.5F, R.drawable.foto_01))
        platillos.add(Platillo("Platillo2", 30.0, 4.5F, R.drawable.foto_02))
        platillos.add(Platillo("Platillo3", 350.0, 2.0F, R.drawable.foto_03))
        platillos.add(Platillo("Platillo4", 250.0, 3.5F, R.drawable.foto_04))
        platillos.add(Platillo("Platillo5", 20.6, 3.5F, R.drawable.foto_05))
        platillos.add(Platillo("Platillo6", 99.8, 5.0F, R.drawable.foto_06))
        //Inicializamos la lista
        lista = findViewById(R.id.lista)
        //Le ponemos un tamaño fijo
        lista?.setHasFixedSize(true)
        //Inicilaizamos el layoutManager en este caso es un LinearLayoutManayer
        layoutManager = LinearLayoutManager(this)
        //Se lo asociamos a la lista (RecyclerView)
        lista?.layoutManager = layoutManager
        //Creamos una variable para escuchar el estado del actionmode
        val callback = object: androidx.appcompat.view.ActionMode.Callback {
            /*
             * Inicializamos el ActionMode
             */
            override fun onCreateActionMode(
                mode: androidx.appcompat.view.ActionMode?,
                menu: Menu?
            ): Boolean {
                //Al adaptador le llamamos la funcion iniciarActionMode()
                adaptador?.iniciarActionMode()
                //Nuestro objeto actionMode lo igualamos al mode
                actionMode = mode
                //Aqui vamos a inflar el menu para usar el icono de delete
                menuInflater.inflate(R.menu.menucontextual, menu)
                //Retornamos true
                return true
            }
            /*
             * Aqui preparamos el menu del ActionMode
             */
            override fun onPrepareActionMode(
                mode: androidx.appcompat.view.ActionMode?,
                menu: Menu?
            ): Boolean {
                //Retornamos false
                return false
            }
            /*
             * Aqui se detecta cuando damos click a la barrita de estado (toolbar) a algun icono del menu
             */
            override fun onActionItemClicked(
                mode: androidx.appcompat.view.ActionMode?,
                item: MenuItem?
            ): Boolean {
                //Cuando seleccionamos algun item
                when(item?.itemId){
                    //En el menu (icono) borrar ponemos un Toast y llamamamos
                    //la funcion eliminarSeleccionados del Adapter
                    R.id.iEliminar ->{
                        adaptador?.eliminarSeleccionados()
                        Toast.makeText(applicationContext, "Eliminar objetos", Toast.LENGTH_SHORT).show()
                    }else -> {
                    //Retornamos true
                    return true
                    }
                }
                //Al adaptador le llamamos la funcion terminarActionMode()
                adaptador?.terminarActionMode()
                //Terminamos el modo del actionMode (p0)
                mode?.finish()
                //Llamamos a la variable para saber que estamos dentro del actionmode
                isActionMode = false
                //Retornamos true
                return true
            }
            /*
             * Aqui se destruye el ActionMode
             */
            override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {
                //Al adaptador le llamamos la funcion destruirActionMode()
                adaptador?.destruirActionMode()
                //Llamamos a la variable para saber que estamos dentro del actionmode
                isActionMode = false
            }
        }
        //Inicializamos el adaptador
        adaptador = Adaptador(platillos, object: ClickListener{
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(applicationContext, platillos.get(index).nombre, Toast.LENGTH_SHORT).show()
            }
        }, object: LongClickListener{
            override fun LongClick(vista: View, index: Int) {
                //Aqui vamos a activar el actionMode si no lo esta mediante if
                if (!isActionMode){
                    //Aqui activamos el ActionMode
                    startSupportActionMode(callback)
                    isActionMode = true
                    //Hacemos las selecciones o deselecciones
                    adaptador?.seleccionarItem(index)
                }else{
                    //Si esta activo solo hacemos las selecciones o deselecciones
                    adaptador?.seleccionarItem(index)
                }
                //Ponemos el titulo del actionMode con los elementos seleccionados
                actionMode?.title = adaptador?.obtenerNumeroElementoSeleccionados().toString() + " seleccionados"
            }
        })
        //Se lo asociamos a la lista (RecyclerView)
        lista?.adapter = adaptador
        //El SwipeRefreshLayout
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swiperefreshlayout)
        //Vamos a escuchar el SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            //Añadimos de ejemplo un nuevo platillo y actualizamos el adaptador
            platillos.add(Platillo("Nuggets", 250.0, 3.5F, R.drawable.foto_01))
            adaptador?.notifyDataSetChanged()
            //Apagamos el circulito (Decimos que ya se actualizo los datos)
            swipeRefresh.isRefreshing = false
        }
    }
}