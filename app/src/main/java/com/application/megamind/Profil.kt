package com.application.megamind

import java.sql.Timestamp

data class Profil(val id_profil: String? = null,val id_user: String, val pseudo_profil: String, val mdp_profil : String, val isadmin_profil : Boolean){
    override fun toString(): String {
        return pseudo_profil
    }
}