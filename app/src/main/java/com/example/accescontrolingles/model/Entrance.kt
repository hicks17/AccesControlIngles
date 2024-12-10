package com.example.accescontrolingles.model

data class Entrance(
    val fecha: String,
    val hora: String,
    val status: String,
    val matricula: String,
    val fullname: String,
    val timeStamp: Long,
    val puerta:Int =0

)

