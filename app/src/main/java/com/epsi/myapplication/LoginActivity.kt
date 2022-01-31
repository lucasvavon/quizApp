package com.epsi.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }

        btn_login.setOnClickListener {
            when {
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Veuillez entrer un email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Veuillez entrer un mot de passe.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email : String = et_login_email.text.toString().trim { it <= ' ' }
                    val password : String = et_login_password.text.toString().trim { it <= ' ' }

                    // On cree une instance et on enregistre un utilisateur avec email et mot de passe
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                                //Si le connexion à reussi
                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Connexion réussite.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra(
                                        "user_id", FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    intent.putExtra(
                                        "email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //Si la connexion n'a pas reussi, affiche une message d'erreur
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "La connexion a échoué, veuillez rééssayer.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }


                }

            }

        }

    }

}