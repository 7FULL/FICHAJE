package com.full.crm.network


import com.full.fichaje.models.Fichaje
import com.full.fichaje.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    /**
     * Tipical function to check if the api is up
     * @return It returns "PON"
     */
    @GET("api/pin")
    suspend fun pin(): Response<DataResponse<String>>

    /**
     * Function to login and get the data for that user
     * @param username The username to log in with
     * @param password The password to log in with
     * @return The employee and status 200 if the login is correct, else returns an empty employee and status 403
     */
    @POST("api/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<DataResponse<User>>

    @POST("api/user/getUser")
    suspend fun getEmployee(@Body loginRequest: LoginRequest): Response<DataResponse<User>>

    @POST("api/user/fichar")
    suspend fun fichar(@Body fichajeRequest: FichajeRequest): Response<DataResponse<Boolean>>
}

data class LoginRequest(val username: String, val password: String)
data class FichajeRequest(val username: String, val fichaje: Fichaje)