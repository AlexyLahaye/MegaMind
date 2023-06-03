package com.application.megamind

import java.sql.Timestamp

class Like(val id_like: String?, val id_contenu_like: String, val id_profil_like: String, val id_etat_like: Int, val createdAt: Timestamp?, val updatedAt: Timestamp?){
    override fun toString(): String {
        return "Like(id_like=$id_like, id_contenu_like='$id_contenu_like', id_profil_like='$id_profil_like', id_etat_like=$id_etat_like, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}