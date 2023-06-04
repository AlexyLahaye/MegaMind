package com.application.megamind

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path


interface LikeRoutes {
    // Création d'un like
    @POST("/like/")
    fun createLike(@Body like : Like): Call<ResponseBody>

    // Suppression d'un like
    @DELETE("/like/{id_contenu_like}/{id_etat_like}")
    fun deleteLike(
        @Path("id_contenu_like") id_contenu_like: String,
        @Path("id_etat_like") id_etat_like: Int
    ): Call<ResponseBody>
}