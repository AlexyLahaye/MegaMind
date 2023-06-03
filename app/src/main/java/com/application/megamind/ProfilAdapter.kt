package com.application.megamind

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProfilAdapter(private var context: Context,
                    private var userProfils:List<List<Profil>>,
                    private val onFrameLayoutClickListener: (position: Int) -> Unit )// Ajout du paramètre du lambda
                                    : RecyclerView.Adapter<ProfilViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilViewHolder {
        val profilView = LayoutInflater.from(parent.context).inflate(R.layout.profil_view, parent, false)
        return ProfilViewHolder(profilView, onFrameLayoutClickListener)
    }

    override fun getItemCount(): Int {
        return userProfils[0].count();
    }

    override fun onBindViewHolder(holder: ProfilViewHolder, position: Int) {
        // Je recupère à la bonne position la bonne catagory
        var unUserProfils = userProfils[0][position]

        //J'écris donc la bonne position le nom de celle-ci
        holder.libelleProfil.setText(unUserProfils.pseudo_profil)
    }


}