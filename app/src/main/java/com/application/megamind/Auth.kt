package com.application.megamind

data class Auth(var label_user : String, val mdp_user : String){
    override fun toString(): String {
        return "$label_user - $mdp_user"
    }
}
