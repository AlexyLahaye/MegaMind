package com.application.megamind

import retrofit2.Call
import retrofit2.http.*

interface ProfilRoutes {

    @GET("/profil/{id_user}")
        fun getAllProfil(@Path("id_user") id_profil: String): Call<List<List<Profil>>>

    @POST("/profil/")
        fun createProfil(@Body profil : Profil): Call<Profil>

    @GET("/profil/pseudoProfil/{pseudo_profil}")
    fun searchProfilByPseudo(@Path("pseudo_profil") pseudo_profil : String): Call<List<Profil>>

    @GET("/profil/idprofil/{id_profil}")
    fun searchProfilById(@Path("id_profil") id_profil : String): Call<List<Profil>>

}
