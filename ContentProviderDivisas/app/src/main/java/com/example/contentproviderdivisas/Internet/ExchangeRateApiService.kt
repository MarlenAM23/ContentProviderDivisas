package com.example.contentproviderdivisas.Internet

import com.example.contentproviderdivisas.Model.Posts
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Definición de la URL base para las llamadas a la API
private const val BASE_URL = "https://open.er-api.com"

// Configuración de Moshi para convertir los datos JSON en objetos Kotlin
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

// Configuración de Retrofit para comunicarse con la API y convertir los datos JSON
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

// Definición de la interfaz que describe las llamadas a la API
interface ExchangeRateApiService {
    // Definición del endpoint de la API para obtener las tasas de cambio
    @GET("v6/latest/USD")
    suspend fun getMonedas(): Moneda

    // Definición de una llamada genérica para obtener los datos de los posts
    val posts : Call<Posts>
}

// Objeto singleton que se utiliza para acceder al servicio de API
object ExchangeApi {
    val retrofitService: ExchangeRateApiService by lazy { retrofit.create(ExchangeRateApiService::class.java) }
}
