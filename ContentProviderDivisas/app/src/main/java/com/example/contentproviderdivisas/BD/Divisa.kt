package com.example.contentproviderdivisas.BD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Divisa(
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,
    var baseCode: String,
    var nombreDivisa: String,
    var valor: Double,
    var fecha: String
)
