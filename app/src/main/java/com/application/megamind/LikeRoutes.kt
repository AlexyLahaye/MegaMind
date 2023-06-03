package com.application.megamind

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class nbLike(var nbLike : Int)
data class isLiked(var isLiked: Boolean)

interface LikeRoutes {
    // Création d'un like
    @POST("/like/")
    fun createLike(@Body like : Like): Call<Like>

    // Route qui renvoie le nombre de like sur un contenu.
    @GET("/like/count/{id_contenu_like}/{id_etat_like}")
    fun getNbLike(
        @Path("id_contenu_like") id_contenu_like: String,
        @Path("id_etat_like") id_etat_like: Int
    ): Call<nbLike>

    // Route qui me retournera un booleen si oui ou non le contenu a été like par l'utilisateur
    @GET("/like/{id_contenu_like}/{id_etat_like}/{id_profil_like}")
    fun getIsLikedByUser(
        @Path("id_contenu_like") id_contenu_like: String,
        @Path("id_etat_like") id_etat_like: Int,
        @Path("id_profil_like") id_profil_like: String): Call<isLiked>

    // Suppression d'un like
    @DELETE("/like/{id_contenu_like}/{id_etat_like}")
    fun deleteLike(
        @Path("id_contenu_like") id_contenu_like : String,
        @Path("id_etat_like") id_etat_like : String)
}