package com.application.megamind

import java.sql.Timestamp
import java.util.Date

class Post (val id_post: String?, val id_profil: String, val contenu_post:String, val sensibilite_post:String, val createdAt:Timestamp,val updatedAt:Timestamp, val pseudo_profil:String, val nbLike:Int, val isLiked: Boolean){
    override fun toString(): String {
        return "Post(id_post=$id_post, id_profil='$id_profil', contenu_post='$contenu_post', sensibilite_post='$sensibilite_post', createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
