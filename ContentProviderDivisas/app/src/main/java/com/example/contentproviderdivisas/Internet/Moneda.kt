package com.example.contentproviderdivisas.Internet

// Importamos la anotación "Json" de la biblioteca Moshi
import com.squareup.moshi.Json

// Esta clase de datos define una foto de Marte que incluye un ID y la URL de la imagen.
// Los nombres de las propiedades de esta clase de datos son utilizados por Moshi para hacer coincidir
// los nombres de los valores en JSON.
data class Moneda(
    val result: String, // El resultado de la solicitud
    val provider: String, // El nombre del proveedor de la API
    val documentation: String, // Un enlace a la documentación de la API
    @Json(name = "terms_of_use") val termsOfUse: String, // Los términos de uso de la API
    @Json(name = "time_last_update_unix") val lastUpdateTime: Long, // El tiempo de la última actualización en formato UNIX
    @Json(name = "time_last_update_utc") val lastUpdateUtc: String, // El tiempo de la última actualización en formato UTC
    @Json(name = "time_next_update_unix") val nextUpdateTime: Long, // El tiempo de la próxima actualización en formato UNIX
    @Json(name = "time_next_update_utc") val nextUpdateUtc: String, // El tiempo de la próxima actualización en formato UTC
    @Json(name = "time_eol_unix") val endOfLifeUnix: Long, // El tiempo de finalización de vida útil en formato UNIX
    @Json(name = "base_code") val baseCode: String, // El código de la moneda base
    val rates: Map<String, Double> // Un mapa de tasas de cambio para diferentes monedas
)