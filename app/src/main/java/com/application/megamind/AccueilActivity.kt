package com.application.megamind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        var pseudoProfil = intent.getStringExtra("pseudoProfil") ?:""

        val listPosts = findViewById<RecyclerView>(R.id.recyclerVPosts)
        listPosts.layoutManager = LinearLayoutManager(this)

        fun getUnProfilByPseudo(pseudo : String, onResponse: (Profil) -> Unit){
            var reqProfil = MegaMindService.profilService.searchProfilByPseudo(pseudo)

            reqProfil.enqueue(object : Callback<List<Profil>>{
                override fun onResponse(call: Call<List<Profil>>, response: Response<List<Profil>>) {
                    val profil = response.body()

                    if(response.code() == 200 && profil != null){
                        onResponse(profil[0])
                    }
                    else{
                        Toast.makeText( this@AccueilActivity,"DDT PROFIL FLOP ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Profil>>, t: Throwable) {
                    val errorMessage = "EXT PROFIL: ${t.message}"
                    Toast.makeText(this@AccueilActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    t.printStackTrace() // Affiche la trace de la pile d'erreurs dans la console
                }
            })
        }

        /*fun updatePost(postGen: PostGen){
            val position = posts.indexOfFirst { it[0].id_post == post.id_post }
            if (position != -1) {
                val newNbLike = nbLike + 1

                //val updatedPost = posts[position][0].updatedAt // Convertir en MutableList
                //updatedPost[0] = newNbLike // Mettre à jour le nombre de likes dans le post
                //posts[position] = updatedPost // Mettre à jour le post dans la liste des posts
                //notifyItemChanged(position) // Notifier l'adaptateur du changement à la position spécifique
            }
        }*/

        fun updateLike(post : Post, profil: Profil){
            var like = post.id_post?.let { profil.id_profil?.let { it1 -> Like(null, it, it1,2, null, null ) } }
            var reqLike: Call<ResponseBody>? = null
            if (post.isLiked != true){
                reqLike = like?.let { MegaMindService.likeService.createLike(it) }
            } else {
                reqLike = post.id_post?.let { MegaMindService.likeService.deleteLike(it, 2) }
            }

            reqLike?.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val body = response.body()
                    if(response.code() == 200){
                        if (body != null){

                        }
                    }
                    else{
                        Toast.makeText(this@AccueilActivity, "INT POSTS Flop", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@AccueilActivity, "EXT LIKE Flop", Toast.LENGTH_SHORT).show()
                }
            })
        }


        fun getAllPostFromFollowedProfilByUserP(profil : Profil){
            var reqPost = MegaMindService.postService.getAllPostFromProfilFollowed(profil.id_profil?:"")

            reqPost.enqueue(object : Callback<MutableList<List<Post>>>{
                override fun onResponse(call: Call<MutableList<List<Post>>>, response: Response<MutableList<List<Post>>>) {
                    val post = response.body()

                    if (response.code() == 200 && post != null) {
                        var listPostGens = post
                        val postAdapter = PostAdapter(listPostGens[0]) { position ->
                            // EVENT
                            val selectedPost = listPostGens[0][position]
                            updateLike(selectedPost, profil)

                        }
                        listPosts.adapter = postAdapter
                    }
                }
                override fun onFailure(call: Call<MutableList<List<Post>>>, t: Throwable) {
                    Toast.makeText(this@AccueilActivity, "EXT POSTS Flop", Toast.LENGTH_SHORT).show()
                }
            })
        }

        getUnProfilByPseudo(pseudoProfil){ profil ->
            getAllPostFromFollowedProfilByUserP(profil)
        }

    }
}