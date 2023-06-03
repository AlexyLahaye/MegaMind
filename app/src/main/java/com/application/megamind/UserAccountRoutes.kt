package com.application.megamind

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface UserAccountRoutes {

    @POST("/auth/login")
    fun login(@Body auth : Auth): Call<AuthentificationResult>
}