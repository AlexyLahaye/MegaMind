package com.application.megamind

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MegaMindService {
    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.18:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userAccountService = retrofit.create(UserAccountRoutes::class.java)
        val profilService = retrofit.create(ProfilRoutes::class.java)
        val postService = retrofit.create(PostRoutes::class.java)
        val likeService = retrofit.create(LikeRoutes::class.java)
    }
}