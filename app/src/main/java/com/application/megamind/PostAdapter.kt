package com.application.megamind

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time
import java.util.*
import java.util.concurrent.TimeUnit


class PostAdapter (private var post:kotlin.collections.List<Post>,
                   private val onFrameLayoutClickListener: (position: Int) -> Unit )// Ajout du paramètre du lambda
    : RecyclerView.Adapter<PostViewHolder>(){

    interface OnLikeUpdatedListener {
        fun onLikeUpdated(postId: String, isLiked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false)
        return PostViewHolder(postView, onFrameLayoutClickListener)
    }

    override fun getItemCount(): Int {
        return post.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val lepost = post[position]

        holder.pseudoProfil.setText(lepost.pseudo_profil)
        holder.contenuPost.setText(lepost.contenu_post)


        if (lepost.isLiked == true) {
            holder.isLiked.setImageResource(R.drawable.likelogo21)
        } else {
            holder.isLiked.setImageResource(R.drawable.likelogo1)
        }
        holder.nbLike.setText(lepost.nbLike.toString())

        val createdAt = lepost.createdAt
        if (createdAt != null) {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy- HH:mm:ss", Locale.getDefault())
            val formattedCreatedAt = dateFormat.format(createdAt)
            val currentDate = Date()
            val formattedCurrentDate = dateFormat.format(currentDate)

            val diffInMillis = currentDate.time - createdAt.time
            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
            val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
            val diffInMonths = diffInDays / 30
            val diffInYears = diffInDays / 365

            val dateDiff = when {
                diffInMinutes < 1 -> "À l'instant"
                diffInHours < 1 -> "Il y a $diffInMinutes minutes"
                diffInDays < 1 -> "Il y a $diffInHours heures"
                diffInMonths < 1 -> "Il y a $diffInDays jours"
                diffInYears < 1 -> "Il y a $diffInMonths mois"
                else -> "Il y a $diffInYears années"
            }
            holder.createdAt.text = dateDiff
        } else {
            holder.createdAt.text = "N/A"
        }
    }

}

private fun ImageView.setImageDrawable(likelogo1: Int) {
}
