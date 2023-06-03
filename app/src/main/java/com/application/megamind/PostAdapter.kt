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

//class PostAdapter (private var context: Context,
//                   private var posts: MutableList<List<Post>>,
//                   private var pseudo_profil: String,
//                   private val nbLike: Int,
//                   private var isLiked: Boolean,
//                   private val onFrameLayoutClickListener: (position: Int) -> Unit )// Ajout du paramètre du lambda
//                            : RecyclerView.Adapter<PostViewHolder>(){
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val postView = LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false)
//        return PostViewHolder(postView, onFrameLayoutClickListener)
//    }
//
//    override fun getItemCount(): Int {
//        return posts[0].count()
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        var unPost = posts[0][position]
//        holder.pseudoProfil.setText(pseudo_profil)
//        holder.contenuPost.setText(unPost.contenu_post)
//
//        if (isLiked == true) {
//            holder.isLiked.setImageResource(R.drawable.likelogo21)
//        } else {
//            holder.isLiked.setImageResource(R.drawable.likelogo1)
//        }
//        holder.nbLike.setText(nbLike.toString())
//
//        val createdAt = unPost.createdAt
//        if (createdAt != null) {
//            val dateFormat = SimpleDateFormat("dd-MM-yyyy- HH:mm:ss", Locale.getDefault())
//            val formattedCreatedAt = dateFormat.format(createdAt)
//            val currentDate = Date()
//            val formattedCurrentDate = dateFormat.format(currentDate)
//
//            val diffInMillis = currentDate.time - createdAt.time
//            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
//            val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
//            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
//            val diffInMonths = diffInDays / 30
//            val diffInYears = diffInDays / 365
//
//            val dateDiff = when {
//                diffInMinutes < 1 -> "À l'instant"
//                diffInHours < 1 -> "Il y a $diffInMinutes minutes"
//                diffInDays < 1 -> "Il y a $diffInHours heures"
//                diffInMonths < 1 -> "Il y a $diffInDays jours"
//                diffInYears < 1 -> "Il y a $diffInMonths mois"
//                else -> "Il y a $diffInYears années"
//            }
//            holder.createdAt.text = dateDiff
//        } else {
//            holder.createdAt.text = "N/A"
//        }
//    }
//
//}
//
//private fun ImageView.setImageDrawable(likelogo1: Int) {
//}

class PostAdapter (private var postGens:kotlin.collections.List<PostGen>,
                   private val onFrameLayoutClickListener: (position: Int) -> Unit )// Ajout du paramètre du lambda
    : RecyclerView.Adapter<PostViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false)
        return PostViewHolder(postView, onFrameLayoutClickListener)
    }

    override fun getItemCount(): Int {
        return postGens.size
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val lepost = postGens[position]

        holder.pseudoProfil.setText(lepost.pseudo_profil)
        holder.contenuPost.setText(lepost.contenu_post)
        Log.d("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL", lepost.contenu_post)

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
