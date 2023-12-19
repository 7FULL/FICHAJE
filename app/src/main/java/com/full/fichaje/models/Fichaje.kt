package com.full.fichaje.models

class Fichaje(
    var diaEntrada: Int,
    var mesEntrada: Int,
    var anoEntrada: Int,
    var diaSalida: Int,
    var mesSalida: Int,
    var anoSalida: Int,
    var horaEntrada: Int = 0,
    var minutoEntrada: Int = 0,
    var horaSalida: Int = 0,
    var minutoSalida: Int = 0
) {

    //Constructor
    constructor(): this(0, 0, 0, 0, 0, 0)
}
