package com.example.contentproviderdivisas

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.work.*
import com.example.contentproviderdivisas.BD.Divisa
import com.example.contentproviderdivisas.BD.DivisaDatabase
import com.example.contentproviderdivisas.Overview.OverviewViewModel
import com.example.contentproviderdivisas.WorkManager.MyWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // Declarar la instancia de la vista de modelo OverviewViewModel
    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Crear la instancia de la base de datos DivisaDatabase y el objeto DivisaDao
        val db = Room.databaseBuilder(
            applicationContext, DivisaDatabase::class.java, "moneda").build()
        val divisaDao = db.divisaDao()

        // Inicializar la vista de modelo OverviewViewModel
        overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        // Programar tarea de actualización de divisas
        //Regla que verifica si esta conectado a internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val actualizacionDivisasWork = PeriodicWorkRequestBuilder<MyWorker>(
            15, TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MyWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            actualizacionDivisasWork
        )

        // Configurar el botón de búsqueda de divisas
        val buscarButton = findViewById<Button>(R.id.btnBuscar)
        buscarButton.setOnClickListener {

            // Ejecutar la búsqueda de divisas en un hilo secundario utilizando coroutines
            lifecycleScope.launch {
                while (true) {
                    // Obtener las tasas de cambio de la vista de modelo
                    val tasasCambio = overviewViewModel.mon

                    // Obtener la fecha actual
                    val f = LocalDate.now().toString()

                    // Insertar cada divisa en la base de datos
                    for ((key, value) in tasasCambio.rates.entries) {
                        val divisa = Divisa(
                            baseCode = tasasCambio.baseCode, nombreDivisa = key, valor = value, fecha = f
                        )
                        withContext(Dispatchers.IO) {
                            divisaDao.insertDivisa(divisa)
                        }
                    }

                    // Esperar 10 minutos antes de realizar una nueva búsqueda
                    delay(600000)
                }
            }
        }
    }
}
