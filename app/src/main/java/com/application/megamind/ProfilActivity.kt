package com.application.megamind

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val context = this

        // Message de succès
        Toast.makeText(this,"Logged in successfully !", Toast.LENGTH_SHORT).show()

        val jwtToken = JWTToken.getInstance(this)
        val token = (JWTToken.getInstance(this@ProfilActivity)).token.replace("Bearer ","")
        val IDdecodedJWT = JWTToken.getIdUser(token)

        fun showAllUserProfils(id: String){
            // La réponse de la requete
            val reqProfil = MegaMindService.profilService.getAllProfil(id!!)

            // Affichage du recycler view
            val listProfils = findViewById<RecyclerView>(R.id.recyclerVProfils)
            listProfils.layoutManager = LinearLayoutManager(this)

            // Exécution de la requête de manière asynchrone
            reqProfil.enqueue(object : Callback<List<List<Profil>>> {
                override fun onResponse(call: Call<List<List<Profil>>>, response: Response<List<List<Profil>>>) {

                    val body = response.body()


                    if (response.code() == 200 && body != null) {
                        if (body[0].size < 4){
                            findViewById<FrameLayout>(R.id.FLAddProfil).visibility = View.VISIBLE
                        }
                        else {
                            findViewById<FrameLayout>(R.id.FLAddProfil).visibility = View.GONE
                        }
                        // La on affiche mes données dans le recycler view
                        listProfils.adapter = ProfilAdapter(this@ProfilActivity, body) { position ->
                            //Evenement sur le FrameLayout
                            val pseudoProfil = body[0][position].pseudo_profil // Récupérer le pseudo du profil cliqué
                            val intent = Intent(context,AccueilActivity::class.java)
                            intent.putExtra("pseudoProfil", pseudoProfil)
                            startActivity(intent)
                        }
s

                    } else {
                        Toast.makeText( this@ProfilActivity,"Error during loading of operation", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<List<List<Profil>>>, t: Throwable) {
                    Toast.makeText( this@ProfilActivity,"Error during loading of operation", Toast.LENGTH_SHORT).show()
                }
            })
        }

        showAllUserProfils(IDdecodedJWT!!)

        findViewById<FrameLayout>(R.id.FLAddProfil).setOnClickListener {
            // Afficher la fenêtre modale
            val dialogView = LayoutInflater.from(this@ProfilActivity).inflate(R.layout.modal_create_profil, null)

            // Créer un AlertDialog
            val dialogBuilder = AlertDialog.Builder(this@ProfilActivity)
                .setView(dialogView)
                .setCancelable(true)

            // Afficher la fenêtre modale
            val dialog = dialogBuilder.create()
            dialog.show()

            // Gérer les actions de la fenêtre modale
            // Ferme la fenêtre modale lorsqu'on appuie sur le bouton "Annuler"
            dialogView.findViewById<Button>(R.id.CancelAddProfilBtn).setOnClickListener {
                dialog.dismiss()
            }

            //Récupération des données du formulaire lorsqu'on appuie sur le bouton "Créer"
            dialogView.findViewById<Button>(R.id.addProfilBtn).setOnClickListener {
                // Récupère les valeurs du formulaire id_user, pseudo_profil, mdp_profil, isadmin_profil, createdAt, updateAt
                val pseudo = dialogView.findViewById<EditText>(R.id.PseudoAddProfil).text.toString()
                val password = dialogView.findViewById<EditText>(R.id.MdpAddProfil).text.toString()

                val profil = Profil(null,IDdecodedJWT,pseudo, password, false)
                val createProfil = MegaMindService.profilService.createProfil(profil)

                createProfil.enqueue(object : Callback<Profil>{
                    override fun onResponse(call: Call<Profil>, response: Response<Profil>) {
                        val body = response.body()
                        if(response.code() == 200){
                            showAllUserProfils(IDdecodedJWT!!)
                        }
                        else{
                            Toast.makeText(this@ProfilActivity, "INT PROFIL Flop", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Profil>, t: Throwable) {
                        Toast.makeText(this@ProfilActivity, "EXT PROFIL Flop", Toast.LENGTH_SHORT).show()
                    }

                })
                // Fermer la fenêtre modale
                dialog.dismiss()
            }
        }
    }
}