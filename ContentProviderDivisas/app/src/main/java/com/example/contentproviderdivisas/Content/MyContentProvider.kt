package com.example.contentproviderdivisas.Content

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.contentproviderdivisas.BD.DivisaDatabase
import com.example.contentproviderdivisas.Repository.DivisaRepository
import com.example.contentproviderdivisas.Repository.Myapplication

// Se define un UriMatcher con las URIs que se utilizarán en el Content Provider
private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {

    // URI para acceder a todas las divisas
    addURI("com.example.contentproviderdivisas", "divisas", 1)

    // URI para acceder a una divisa específica por su ID
    addURI("com.example.contentproviderdivisas", "divisas/#", 2)

    // URI para acceder a una divisa específica por su nombre
    addURI("com.example.contentproviderdivisas", "divisas/*", 3)
}
class MyContentProvider : ContentProvider() {

    // Variables que se utilizarán para acceder a la base de datos y al repositorio de divisas
    lateinit var repository: DivisaRepository
    lateinit var db: DivisaDatabase

    override fun onCreate(): Boolean {
        // Se inicializan las variables de acceso a la base de datos y al repositorio de divisas
        repository = (context as Myapplication).repositoryMoneda
        db = (context as Myapplication).database
        return true
    }

    // Método que se llama al realizar una consulta al Content Provider
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null

        // Se determina el tipo de consulta a partir de la URI recibida
        when (sUriMatcher.match(uri)) {
            // Si la URI es la de acceso a todas las divisas
            1 -> {
                cursor = db.divisaDao().getAllCursor()
            }
            // Si la URI es la de acceso a una divisa específica por su ID
            2 -> {

            }
            // Si la URI es la de acceso a una divisa específica por su nombre
            3 -> {

            }
            // Si la URI no se reconoce, se lanza una excepción
            else -> throw IllegalArgumentException("URI desconocida: $uri")
        }

        // Se establece el observador del cursor para notificar a los observadores registrados
        // cuando los datos cambien
        //Notifica cuando los datos cambian
        cursor?.setNotificationUri(context?.contentResolver, uri)

        // Se devuelve el cursor con los datos solicitados
        return cursor
    }

    override fun getType(uri: Uri): String? {
        // TODO: Implementar si se requiere el acceso a múltiples tipos de datos
        throw NotImplementedError("Método no implementado")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        // TODO: Implementar si se requiere la eliminación de datos
        throw NotImplementedError("Método no implementado")
    }



    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }
}