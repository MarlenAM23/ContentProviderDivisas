package com.example.contentproviderdivisas.Repository

import android.app.Application
import com.example.contentproviderdivisas.BD.DivisaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Myapplication : Application() {
    // Crea un alcance global para las corrutinas
    val applicationScope = CoroutineScope(SupervisorJob())

    // Crea una instancia de la base de datos de DivisaDatabase y el repositorio DivisaRepository
    val database by lazy { DivisaDatabase.getDatabase(this, applicationScope) }
    val repositoryMoneda by lazy { DivisaRepository(database.divisaDao()) }

    override fun onCreate() {
        super.onCreate()
        // Aquí se pueden realizar algunas configuraciones adicionales
    }
}
