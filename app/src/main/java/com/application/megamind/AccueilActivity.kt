package com.application.megamind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        fun getUnProfilById(posts : List<List<Post>>, callback: (Profil) -> Unit){
            var reqProfil = MegaMindService.profilService.searchProfilById(posts[0][0].id_profil)

            reqProfil.enqueue(object : Callback<List<Profil>>{
                override fun onResponse(
                    call: Call<List<Profil>>,
                    response: Response<List<Profil>>
                ) {
                    val profil = response.body()

                    if(response.code() == 200 && profil != null) {
                        callback(profil[0])
                    }
                    else {
                        Toast.makeText( this@AccueilActivity,"DDT POSTS FLOP", Toast.LENGTH_SHORT).show()
                    }

                }
                override fun onFailure(call: Call<List<Profil>>, t: Throwable) {
                    Toast.makeText( this@AccueilActivity,"EXT POSTS Flop", Toast.LENGTH_SHORT).show()
                }
            })
        }


        fun getNbLike(post: Post, callback: (Int) -> Unit) {
            val reqLike = post.id_post?.let { MegaMindService.likeService.getNbLike(it, 1) }

            reqLike?.enqueue(object : Callback<nbLike> {
                override fun onResponse(call: Call<nbLike>, response: Response<nbLike>) {
                    val nbLike = response.body()
                    if (response.code() == 200) {
                        if (nbLike != null) {
                            callback(nbLike.nbLike) // Appel du callback avec la valeur du nombre de likes
                        } else {
                            // Gestion de l'erreur si le nombre de likes est null
                            Toast.makeText(this@AccueilActivity, "INT LIKE Flop", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Gestion de l'erreur en cas de réponse non réussie
                        Toast.makeText(this@AccueilActivity, "EXT INT LIKE Flop", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<nbLike>, t: Throwable) {
                    // Gestion de l'erreur en cas d'échec de la requête
                    Toast.makeText(this@AccueilActivity, "EXT LIKE Flop", Toast.LENGTH_SHORT).show()
                }
            })
        }

        fun isLikedByUser(post: Post, profil: Profil, callback: (Boolean) -> Unit){
            val reqLike = post.id_post?.let { profil.id_profil?.let { it1 -> MegaMindService.likeService.getIsLikedByUser(it, 1, it1 )}}

            reqLike?.enqueue(object : Callback<isLiked>{
                override fun onResponse(call: Call<isLiked>, response: Response<isLiked>) {
                    val isLiked = response.body()
                    if(response.code() == 200){
                        if (isLiked != null) {
                            callback(isLiked.isLiked)
                        }
                    }
                    else {
                        Toast.makeText(this@AccueilActivity, "INT LIKED Flop", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<isLiked>, t: Throwable) {
                    Toast.makeText(this@AccueilActivity, "EXT LIKED Flop", Toast.LENGTH_SHORT).show()
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

        fun addlike(post : Post, profil: Profil, nbLike: nbLike, postAdapter: PostAdapter, isLiked: isLiked){
            var like = post.id_post?.let { profil.id_profil?.let { it1 -> Like(null, it, it1,1, null, null ) } }
            var reqLike = like?.let { MegaMindService.likeService.createLike(it) }

            reqLike?.enqueue(object : Callback<Like>{
                override fun onResponse(call: Call<Like>, response: Response<Like>) {
                    val body = response.body()
                    if(response.code() == 200){
                        if (body != null){


                        }
                    }
                    else{
                        Toast.makeText(this@AccueilActivity, "INT POSTS Flop", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Like>, t: Throwable) {
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
                        var listPostGens : MutableList<PostGen> = mutableListOf()
                        for(uneListPosts in post){
                            Log.d("unPost", uneListPosts.toString())
                            for(unPost in uneListPosts){
                                Log.d("unPost", unPost.toString())
                                //TOUT LES APPELS DE FONCTIONS AFIN D'AFFICHER LES POSTS !
                                getUnProfilById(post) { profil ->
                                    val pseudo_profil = profil.pseudo_profil
                                    getNbLike(post[0][0]) { nbLike ->
                                        isLikedByUser(post[0][0], profil) { isLiked ->
                                            val postGen = PostGen(unPost.id_post,unPost.id_profil,unPost.contenu_post, unPost.sensibilite_post, unPost.createdAt, unPost.updatedAt,pseudo_profil, nbLike, isLiked)
                                            Log.d("POSTGEN", postGen.toString())
                                            listPostGens.add(postGen)
                                            // FAIRE LE TOUT EN BACK END AFIN D'AVOIR UNE LISTE D'OBJET POST

                                        }
                                    }
                                }
                            }
                        }
                        val postAdapter = PostAdapter(listPostGens) { position ->
                            // EVENT
                        }
                        listPosts.adapter = postAdapter




//                        if (!post[0].isEmpty()) {
//                            val postAdapterCallback: (String, Int, Boolean) -> Unit = { pseudo_profil, nbLike, isLiked ->
//                                // Création de l'instance de l'adaptateur avec les posts et les autres données
//                                val postAdapter = PostAdapter(this@AccueilActivity, post, pseudo_profil, nbLike, isLiked) { position ->
//                                    //Evenement sur le FrameLayout
//                                    //val postGen = PostGen(post.id_post, post.id_profil, post.contenu_post, post.sensibilite_post, post.createdAt, post.updatedAt, nbLike.nbLike, isLiked.isLiked)
//                                }
//                                // Assignation de l'adaptateur au RecyclerView
//                                listPosts.adapter = postAdapter
//                            }
//                            //TOUT LES APPELS DE FONCTIONS AFIN D'AFFICHER LES POSTS !
//                            getUnProfilById(post) { profil ->
//                                val pseudo_profil = profil.pseudo_profil
//                                getNbLike(post[0][0]) { nbLike ->
//                                    isLikedByUser(post[0][0], profil) { isLiked ->
//                                        postAdapterCallback(pseudo_profil, nbLike, isLiked)
//                                    }
//                                }
//                            }


//                        } else {
//                            Toast.makeText(
//                                this@AccueilActivity,
//                                "DDT POSTS FLOP",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
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