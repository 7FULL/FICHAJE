package com.full.crm.ui.theme.agenda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.full.crm.network.API
import com.full.crm.network.DataResponse
import com.full.crm.network.FichajeRequest
import com.full.fichaje.models.Fichaje
import com.full.fichaje.models.User
import com.full.fichaje.navigation.NavigationManager
import kotlinx.coroutines.launch
import java.util.Calendar

class FicharViewModel: ViewModel() {
    //Fecha de entrada
    private val _fechaEntrada = MutableLiveData(Calendar.getInstance())
    val fechaEntrada: LiveData<Calendar> = _fechaEntrada

    //Fecha de salida
    private val _fechaSalida = MutableLiveData(Calendar.getInstance())
    val fechaSalida: LiveData<Calendar> = _fechaSalida

    //Ha fichado
    private val _haFichado = MutableLiveData(false)
    val haFichado: LiveData<Boolean> = _haFichado

    //Ficho entrada y salida
    private val _ficharEntrada = MutableLiveData(false)
    val ficharEntrada: LiveData<Boolean> = _ficharEntrada

    //Todos los fichajes del usuario
    private val _fichajes = MutableLiveData(emptyList<Fichaje>())
    val fichajes: LiveData<List<Fichaje>> = _fichajes

    //Fichaje
    private val _fichaje = MutableLiveData<Fichaje>()

    //Error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        //Comprobamos si del usuario logueado ha fichado hoy
        //Si ha fichado, cambiamos el valor de _haFichado a true y cambiamos el valor de _fechaEntrada y _fechaSalida
        //Si solo ha fichado una vez, cambiamos el valor de _fechaEntrada

        _fichajes.value = API.User.value!!.fichajes

        var aux: Fichaje? = null
        val diaActual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val mesActual = Calendar.getInstance().get(Calendar.MONTH)
        val anoActual = Calendar.getInstance().get(Calendar.YEAR)

        for (fichaje in _fichajes.value!!) {
            val diaFichaje = fichaje.diaEntrada
            val mesFichaje = fichaje.mesEntrada
            val anoFichaje = fichaje.anoEntrada

            if (diaActual == diaFichaje && mesActual == mesFichaje && anoActual == anoFichaje) {
                aux = fichaje
            }
        }

        //Si ha fichado
        if (aux != null) {
            _ficharEntrada.value = true
            _fechaEntrada.value!!.set(Calendar.DAY_OF_MONTH, aux.diaEntrada)
            _fechaEntrada.value!!.set(Calendar.MONTH, aux.mesEntrada)
            _fechaEntrada.value!!.set(Calendar.YEAR, aux.anoEntrada)
            _fechaEntrada.value!!.set(Calendar.HOUR_OF_DAY, aux.horaEntrada)
            _fechaEntrada.value!!.set(Calendar.MINUTE, aux.minutoEntrada)

            //Si ha fichado salida
            if (aux.diaSalida != -1) {
                _haFichado.value = true
                _fechaSalida.value!!.set(Calendar.DAY_OF_MONTH, aux.diaSalida)
                _fechaSalida.value!!.set(Calendar.MONTH, aux.mesSalida)
                _fechaSalida.value!!.set(Calendar.YEAR, aux.anoSalida)
                _fechaSalida.value!!.set(Calendar.HOUR_OF_DAY, aux.horaSalida)
                _fechaSalida.value!!.set(Calendar.MINUTE, aux.minutoSalida)
            }
        }
    }

    //Funcion para fichar
    fun fichar(calendar: Calendar = Calendar.getInstance()) {
        if (_ficharEntrada.value!!) {
            //Si no ha fichado
            _fichaje.value = Fichaje()

            _fichaje.value!!.diaSalida = fechaSalida.value!!.get(Calendar.DAY_OF_MONTH)
            _fichaje.value!!.mesSalida = fechaSalida.value!!.get(Calendar.MONTH)
            _fichaje.value!!.anoSalida = fechaSalida.value!!.get(Calendar.YEAR)
            _fichaje.value!!.horaSalida = fechaSalida.value!!.get(Calendar.HOUR_OF_DAY)
            _fichaje.value!!.minutoSalida = fechaSalida.value!!.get(Calendar.MINUTE)

            _fichaje.value!!.diaEntrada = _fechaEntrada.value!!.get(Calendar.DAY_OF_MONTH)
            _fichaje.value!!.mesEntrada = _fechaEntrada.value!!.get(Calendar.MONTH)
            _fichaje.value!!.anoEntrada = _fechaEntrada.value!!.get(Calendar.YEAR)
            _fichaje.value!!.horaEntrada = _fechaEntrada.value!!.get(Calendar.HOUR_OF_DAY)
            _fichaje.value!!.minutoEntrada = _fechaEntrada.value!!.get(Calendar.MINUTE)
        }
        else {
            _fichaje.value = Fichaje()

            _fichaje.value!!.diaEntrada = _fechaEntrada.value!!.get(Calendar.DAY_OF_MONTH)
            _fichaje.value!!.mesEntrada = _fechaEntrada.value!!.get(Calendar.MONTH)
            _fichaje.value!!.anoEntrada = _fechaEntrada.value!!.get(Calendar.YEAR)
            _fichaje.value!!.horaEntrada = _fechaEntrada.value!!.get(Calendar.HOUR_OF_DAY)
            _fichaje.value!!.minutoEntrada = _fechaEntrada.value!!.get(Calendar.MINUTE)

            _fichaje.value!!.diaSalida = -1
        }

        viewModelScope.launch {
            //Fichamos
            val response = API.service.fichar(FichajeRequest(API.User.value!!.username, _fichaje.value!!))

            if (response.isSuccessful) {
                val data: DataResponse<Boolean> = response.body()!!

                if (data.code == 200) {
                    if (data.data == true) {
                        _error.value = "Fichado correctamente"

                        Log.i("CRM", "Fichado correctamente")
                        NavigationManager.instance?.navigate("fichajes")
                    }
                    else {
                        _error.value = "No se ha podido fichar"

                        Log.i("CRM", "No se ha podido fichar")
                    }
                }
                else{
                    _error.value = """Si ves este mensaje, es que algo ha ido mal. Diselo al del clavito (Pablo, el novio de ISA .-.)${"\n"}
                            Error: $data"""

                    Log.i("CRM", "Error al fichar")
                    Log.i("CRM", response.toString())
                }
            } else {
                _error.value = """Si ves este mensaje, es que algo ha ido mal. Diselo al del clavito (Pablo, el novio de ISA .-.)${"\n"}
                            Error: $response"""
                Log.i("CRM", "Error al fichar")
                Log.i("CRM", response.toString())
            }
        }
    }
}