package com.full.crm.ui.theme.agenda

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.full.fichaje.OptionsBar
import java.util.Calendar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Fichajes(modifier: Modifier = Modifier, ficharViewModel: FicharViewModel) {
    val haFichado: Boolean by ficharViewModel.haFichado.observeAsState(false)
    val haFichadoEntrada: Boolean by ficharViewModel.ficharEntrada.observeAsState(false)

    val fichajeEntrada: Calendar by ficharViewModel.fechaEntrada.observeAsState(Calendar.getInstance())
    val fichajeSalida: Calendar by ficharViewModel.fechaSalida.observeAsState(Calendar.getInstance())

    Scaffold(bottomBar = { OptionsBar(modifier = Modifier.padding(top = 5.dp),selectedIcon = 2) }) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(top = 300.dp)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                if (!haFichado && !haFichadoEntrada) {
                    Text(text = "No has fichado hoy")
                } else if (!haFichado && haFichadoEntrada) {
                    Text(text = "Hora de entrada: ${if (fichajeEntrada.get(Calendar.HOUR_OF_DAY)+1 < 10) "0${fichajeEntrada.get(Calendar.HOUR_OF_DAY)+1}" else fichajeEntrada.get(Calendar.HOUR_OF_DAY)+1}:" +
                            "${if (fichajeEntrada.get(Calendar.MINUTE) < 10) "0${fichajeEntrada.get(Calendar.MINUTE)}" else fichajeEntrada.get(Calendar.MINUTE)}")
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                if (!haFichado && !haFichadoEntrada) {
                    Button(onClick = {
                        ficharViewModel.fichar()
                    }) {
                        Text(text = "Fichar entrada")
                    }
                } else if (!haFichado && haFichadoEntrada) {
                    Button(onClick = {
                        ficharViewModel.fichar()
                    }) {
                        Text(text = "Fichar salida")
                    }
                }else{
                    Text(text = "Ya has fichado hoy")
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                if (haFichado){
                    Text(text = "Has trabajado ${fichajeSalida.get(Calendar.HOUR_OF_DAY) - fichajeEntrada.get(Calendar.HOUR_OF_DAY)} horas y ${fichajeSalida.get(Calendar.MINUTE) - fichajeEntrada.get(Calendar.MINUTE)} minutos")
                }
            }
            //Contador con el tiempo que lleva trabajado hoy
            Row {

            }
        }
    }
}