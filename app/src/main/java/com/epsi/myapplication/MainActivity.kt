package com.epsi.myapplication

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.provider.BaseColumns
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.btn_delete
import kotlinx.android.synthetic.main.activity_main.btn_logout
import kotlinx.android.synthetic.main.activity_storage.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        tv_user_id.text = "Identifiant : $userId"
        tv_email_id.text = "Email : $emailId"




        btn_logout.setOnClickListener {
            //deconnexion
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Êtes-vous sûr ?")
                .setCancelable(false)
                .setPositiveButton("Oui") { dialog, id ->
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
        }

        //suppression d'un compte
        btn_delete.setOnClickListener {

            val user = Firebase.auth.currentUser!! //user = le compte connecté

            //Pop-up de confirmation de suppression de compte
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Êtes-vous sûr ?")
                .setCancelable(false)
                .setPositiveButton("Oui") { dialog, id ->
                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Compte utilisateur supprimé.")
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                finish()
                            }
                        }

                }
                .setNegativeButton("Non") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }
    }


    fun play(view: android.view.View){
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    fun changeImage(view: android.view.View) {
        val intent = Intent(this, StorageActivity::class.java)
        startActivity(intent)
    }



}


