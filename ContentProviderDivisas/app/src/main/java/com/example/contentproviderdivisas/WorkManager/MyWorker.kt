package com.example.contentproviderdivisas.WorkManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.contentproviderdivisas.BD.Divisa
import com.example.contentproviderdivisas.BD.DivisaDatabase
import com.example.contentproviderdivisas.Internet.ExchangeRateApiService
import com.example.contentproviderdivisas.Model.Posts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Clase MyWorker que extiende de la clase Worker de WorkManager
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    // Se crea un scope de Coroutine para manejar las coroutines
    val applicationScope = CoroutineScope(SupervisorJob())

    // Método que se ejecutará en segundo plano cuando se llame al worker
    override fun doWork(): Result {

        // Se crea un objeto Retrofit con la URL base y el converter para el JSON
        val retrofit = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Se crea una instancia de la API del servicio web
        var api: ExchangeRateApiService = retrofit.create(ExchangeRateApiService::class.java)

        // Se llama a la API para obtener los valores de las divisas
        var call: Call<Posts> = api.posts
        call.enqueue(object : Callback<Posts> {

            // Si la llamada a la API es exitosa, se ejecuta este método
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (!response.isSuccessful) {
                    return
                }

                // Se obtiene el cuerpo de la respuesta
                var post = response.body()

                // Se crea una instancia de Divisa para guardar los datos de cada divisa
                var moneda = Divisa(
                    _id = 0,
                    baseCode = "",
                    nombreDivisa = "",
                    valor = 0.0,
                    fecha = ""
                )

                // Se itera sobre el mapa de conversiones obtenido de la respuesta de la API
                for (codes in post!!.conversion_ratesonversions) {

                    // Se asignan los valores de la divisa a la instancia de Divisa
                    moneda.baseCode = codes.key
                    moneda.valor = codes.value

                    // Se inserta la instancia de Divisa en la base de datos
                    DivisaDatabase.getDatabase(applicationContext, applicationScope).divisaDao()
                        .insertDivisa(moneda)
                }
            }

            // Si la llamada a la API falla, se ejecuta este método
            override fun onFailure(call: Call<Posts>, t: Throwable) {
            }
        })

        // Se retorna el resultado de la operación del worker
        return Result.success()
    }
}
