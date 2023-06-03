package com.application.megamind

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class PostViewHolder(row : View, onFrameLayoutClickListener: (position: Int) -> Unit) : RecyclerView.ViewHolder(row){
    var pseudoProfil: TextView = itemView.findViewById(R.id.pseudoProfilPost)
    var nbLike : TextView = itemView.findViewById(R.id.nbLikePost)
    var isLiked: ImageView = itemView.findViewById(R.id.imgLikePost)
    var createdAt: TextView = itemView.findViewById(R.id.dateCreaPost)
    var contenuPost: TextView = itemView.findViewById(R.id.contenuPost)
    val frameLayoutClickable: FrameLayout = itemView.findViewById(R.id.FLPost)
    init {
        frameLayoutClickable.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onFrameLayoutClickListener(position)
            }
        }
    }
}