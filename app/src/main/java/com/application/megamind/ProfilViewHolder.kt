package com.application.megamind

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfilViewHolder(row : View, onFrameLayoutClickListener: (position: Int) -> Unit) : RecyclerView.ViewHolder(row){
    var libelleProfil: TextView = itemView.findViewById(R.id.LibelleProfil)
    val frameLayoutClickable: FrameLayout = itemView.findViewById(R.id.FLProfil)

    init {
        frameLayoutClickable.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onFrameLayoutClickListener(position)
            }
        }
    }
}
