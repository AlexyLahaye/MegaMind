package com.application.megamind

import com.google.gson.annotations.SerializedName

data class AuthentificationResult(@SerializedName("token") val token: String) {
    override fun toString(): String {
        return token
    }
}