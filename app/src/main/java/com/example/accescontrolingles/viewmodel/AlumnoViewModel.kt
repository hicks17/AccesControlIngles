package com.example.accescontrolingles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.accescontrolingles.model.Alumno
import com.example.accescontrolingles.model.Entrance
import com.example.accescontrolingles.network.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlumnoViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl

) : ViewModel() {
    private val _userData = MutableStateFlow(Alumno(uid = "", matricula ="", nombre = "", correo = "", apellido = "", isIn = false,
        apellidoAlumno = "", nombreAlumno = ""))
    val userData = _userData.asStateFlow()

    private val _lastEntranceList = MutableStateFlow(listOf<Entrance>())
    val lastEntranceList = _lastEntranceList.asStateFlow()

    private val _isAuthorized : MutableSharedFlow<Int> = MutableSharedFlow()
    val isAuthorized = _isAuthorized.asSharedFlow()

    var uid = MutableStateFlow("")


    fun reserAuthState(){
        viewModelScope.launch {
            _isAuthorized.emit(2)
        }
    }
    fun createUser(user: Alumno) {
        viewModelScope.launch {
            userRepository.createUser(user)
            _userData.update { user }

        }

    }

    fun getLastEntrance() {
        viewModelScope.launch {
            _lastEntranceList.update {
                userRepository.getLastEntrance()
            }
        }
    }

    fun changeStatus(uid: String) {
        viewModelScope.launch {
            userRepository.changeStatus(uid)
        }
    }

    fun getLastEntrancesByUserID(userId: String) {
        viewModelScope.launch {
            _lastEntranceList.update {
                userRepository.getLastEntrancesByUserID(userId)
            }
        }
    }

    fun authorizeEntrance(correo: String) {

        viewModelScope.launch {
                when (userRepository.authorization(correo)) {
                    true -> _isAuthorized.emit(1)
                    false -> _isAuthorized.emit(0)
                }
            }

        }


    fun setUser(uid: String) {
        viewModelScope.launch {
            _userData.update {
                userRepository.getUser(uid)
            }
            _lastEntranceList.update {
                userRepository.getLastEntrancesByUserID(uid)
            }
        }
    }
}