package com.application.megamind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import kotlin.concurrent.thread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val context = this

        findViewById<Button>(R.id.LoginBtn).setOnClickListener {
            val progressBar = findViewById<ProgressBar>(R.id.LoginProgressBar)
            val loginForm = findViewById<LinearLayout>(R.id.loginForm)

            // INFO FORMULAIRE
            val login = findViewById<TextInputEditText>(R.id.LoginName).text.toString()
            val pwd = findViewById<TextInputEditText>(R.id.LoginPwd).text.toString()

            val auth = Auth(login, pwd)
            val reqLogin = MegaMindService.userAccountService.login(auth)

            thread {
                // AFFICHAGE DU CHARGEMENT
                runOnUiThread{
                    loginForm.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }

                val jwtToken : JWTToken = JWTToken.getInstance(context)

                // Requete de la donnée
                reqLogin.enqueue(object : Callback<AuthentificationResult>{
                    override fun onResponse(
                        call: Call<AuthentificationResult>,
                        response: Response<AuthentificationResult>
                    ) {
                        Log.d("JE SUIS DANS LE ON RESPONSE", String())
                        val body = response.body()
                        if (response.code() == 200 && (body?.token != null && body.token != "")) {
                            jwtToken.setTokenValue(body.token)

                            val intent = Intent(context, ProfilActivity::class.java)
                            startActivity(intent)
                        } else {
                            jwtToken.setTokenValue("")
                            Tools.displayError(context, "Echec de l'enregistrement du token")
                        }
                    }

                    override fun onFailure(call: Call<AuthentificationResult>, t: Throwable) {
                        Log.d("OnFailure", t.toString())
                        jwtToken.setTokenValue("")
                        Tools.displayError(context, "Echec tout cour", )
                    }
                })
                // Affichage donnée
                runOnUiThread {
                    loginForm.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }

            }
        }

    }
}