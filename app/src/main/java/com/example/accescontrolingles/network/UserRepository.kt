package com.example.accescontrolingles.network

import com.example.accescontrolingles.model.Alumno
import com.example.accescontrolingles.model.Entrance

interface UserRepository {

    suspend fun createUser(user: Alumno)

    suspend fun getUser(userId: String): Alumno

    suspend fun updateUser(user: Alumno)

    suspend fun deleteUser(userId: String)

    suspend fun getLastEntrancesByUserID(userId: String): List<Entrance>

    suspend fun getLastEntrance():List<Entrance>

    suspend fun changeStatus(userId: String)

    suspend fun authorization(correo: String): Boolean
}