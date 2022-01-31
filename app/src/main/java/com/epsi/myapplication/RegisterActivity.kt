package com.epsi.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        tv_login.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Veuillez entrer un email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Veuillez entrer un mot de passe.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email : String = et_register_email.text.toString().trim { it <= ' ' }
                    val password : String = et_register_password.text.toString().trim { it <= ' ' }

                    // On cree une instance et on enregistre un utilisateur avec email et mot de passe
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener (
                            OnCompleteListener<AuthResult> { task ->

                                //Si l'inscription à reussi
                                if (task.isSuccessful) {

                                    //Firebase a enregistré un utilisateur
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(this@RegisterActivity, "Inscription réussite.", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //Si l'enregistrement n'a pas reussi
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "L'incription a echoué, veuillez rééssayer.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                        .addOnFailureListener { it->
                            it.printStackTrace()
                        }

                }

            }

        }
    }
}