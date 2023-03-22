package com.example.contentproviderdivisas.Overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contentproviderdivisas.Internet.ExchangeApi
import com.example.contentproviderdivisas.Internet.Moneda
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {
    lateinit var mon: Moneda // Declaración de una variable lateinit llamada mon del tipo Moneda.

    init {
        getMonedasValor() // Llamada a la función getMonedasValor() en la inicialización del objeto.
    }

    // Declaración de la función getMonedasValor() usando la librería kotlinx.coroutines
    fun getMonedasValor() {
        viewModelScope.launch { // Creación de un nuevo hilo de ejecución en viewModelScope
            try {
                mon = ExchangeApi.retrofitService.getMonedas() // Obtenemos la respuesta del API y la asignamos a la variable mon

            } catch (_: Exception) { // En caso de error simplemente lo ignoramos

            }
        }
    }
}
