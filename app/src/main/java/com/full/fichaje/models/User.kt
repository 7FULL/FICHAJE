package com.full.fichaje.models

data class User(
    val _id: String,
    val username: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val fichajes: ArrayList<Fichaje>,
    val role: Role,
){ constructor(): this("", "", "", "", "", "", ArrayList(), Role.NONE) }