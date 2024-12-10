package com.example.accescontrolingles.network

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.example.accescontrolingles.model.Alumno
import com.example.accescontrolingles.model.Entrance
import com.example.accescontrolingles.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDb: DatabaseReference
) : UserRepository {

    override suspend fun createUser(user: Alumno) {
        if (userDb.child(user.correo.replace(".", ",")).get().await().exists()){
            Log.e("UserRepositoryImpl", "createUser: User already exists")
        }else{
            userDb.child(user.correo.replace(".", ",")).child("userData").setValue(user)
        }

    }

    override suspend fun getUser(userId: String): Alumno {
        return withContext(Dispatchers.IO) {
            userDb.child(userId.replace(".", ",")).child("userData").get().await().toAlumno()
        }
    }

    override suspend fun updateUser(user: Alumno) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getLastEntrancesByUserID(userId: String): List<Entrance> {
        return withContext(Dispatchers.IO) {
            userDb.child(userId.replace(".", ",")).child("entradas").get().await().children.map {
                it.toEntrance()
            }
        }
    }

    override suspend fun getLastEntrance(): List<Entrance> {
       return withContext(Dispatchers.IO) {
           userDb.child("lastEntrance").get().await().children.map {
               it.toEntrance()
           }
       }
    }

    override suspend fun changeStatus(userId: String) {
        val status = userDb.child(userId.replace(".", ",")).child("userData").child("in").get().await().value as Boolean
        userDb.child(userId.replace(".", ",")).child("userData").child("in").setValue(!status)
    }

    override suspend fun authorization(correo: String): Boolean {
        val timeStart = Utils.getCurrentTimestamp()
        val fecha = Utils.getCurrentFormattedDate()
        val hora = Utils.getCurrentFormattedTime()
        var fullname = userDb.child(correo.replace(".", ",")).child("userData").child("nombre").get().await().value as String
        fullname += " " + userDb.child(correo.replace(".", ",")).child("userData").child("apellido").get().await().value as String
        val status = userDb.child(correo.replace(".", ",")).child("userData").child("in").get().await().value as Boolean
        val entrada: Entrance = if (status){
            Entrance(fecha, hora,"Salida", correo, fullname, timeStart)
        }else{
            Entrance(fecha, hora,"Entrada", correo, fullname, timeStart)
        }


        if (userDb.child(correo).get().await().exists()){
            changeStatus(correo)
            userDb.child(correo).child("entradas").child(timeStart.toString()).setValue(entrada)
            userDb.child("lastEntrance").child(timeStart.toString()).setValue(entrada)
            return true
        }else{
            return false
        }
    }




}

private fun DataSnapshot.toAlumno() : Alumno {
    return Alumno(
        uid = child("uid").value as String,
        nombre = child("nombre").value as String,
        matricula = child("matricula").value as String,
        apellido = child("apellido").value as String,
        correo = child("correo").value as String,
        isIn = child("in").value as Boolean,
        apellidoAlumno = child("apellidoAlumno").value as String,
        nombreAlumno = child("nombreAlumno").value as String
    )
}

private fun DataSnapshot.toEntrance() : Entrance {
    return Entrance(
        fecha = child("fecha").value as String,
        hora = child("hora").value as String,
        status = child("status").value as String,
        matricula = child("matricula").value as String,
        fullname = child("fullname").value as String,
        timeStamp = child("timeStamp").value as Long)
}
