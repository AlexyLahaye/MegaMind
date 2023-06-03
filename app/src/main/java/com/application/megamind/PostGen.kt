package com.application.megamind

import java.sql.Timestamp

// CREATION DE POST UPDATE AVEC LES VAL NBLIKE ET BOOL
data class PostGen (val id_post: String?, val id_profil: String, val contenu_post:String, val sensibilite_post:String, val createdAt: Timestamp?, val updatedAt: Timestamp?,val pseudo_profil: String, val nbLike : Int, val isLiked : Boolean){
}