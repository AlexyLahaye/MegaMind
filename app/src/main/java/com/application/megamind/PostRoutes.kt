package com.application.megamind

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostRoutes {

    @GET("/post/{id_profil}")
    fun getAllPostFromProfilFollowed(@Path("id_profil") id_profil: String): Call<MutableList<List<Post>>>
}