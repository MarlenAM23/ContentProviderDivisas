package com.example.contentproviderdivisas.Repository

import com.example.contentproviderdivisas.BD.Divisa
import com.example.contentproviderdivisas.BD.DivisaDao
import kotlinx.coroutines.flow.Flow

class DivisaRepository (private val divisaDao: DivisaDao){
    val allMonedas: Flow<List<Divisa>>
    get() = divisaDao.getAll()
}