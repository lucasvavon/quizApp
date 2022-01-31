package com.epsi.myapplication

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_quiz.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class QuizActivity : AppCompatActivity() {

    var quizs = ArrayList<Quiz>()
    var numOfGoodAnswers : Int = 0
    var currentQuizIndex: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    var current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    var formatted = current.format(formatter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        quizs.add(Quiz("Quel est le monument le plus visité au monde ?",
            "Notre-Dame de Paris", "Le Taj Mahal", "Les Pyramides de Gizeh", "La Grande Muraille de Chine", 1))
        quizs.add(Quiz("Quelle est la capitale du Venezuela ?",
            "Washington", "Montevideo", "Caracas", "Bogota", 3))
        quizs.add(Quiz("Qui a gagné le prix Nobel de littérature en 1957 ?",
            "Jean-Paul Sartre", "Albert Camus", "Alexandre Soljenitsyne", "Bob Dylan", 2))
        quizs.add(Quiz("Qui a été élu homme le plus riche du monde en 2020 ?",
            "Elon Musk", "Bernard Arnault", "Jeff Bezos", "Bill Gates", 3))
        quizs.add(Quiz("Quel pays a consommé les plus de pizza en 2020 ?",
            "Les Etats-Unis", "L'Italie", "La France", "Les Pays-Bas", 1))

        quizs.shuffle() //Mélange le questionnaire

        showQuestion(quizs.get(currentQuizIndex))
    }

    private fun showQuestion(quiz: Quiz) {
        txtQuestion.text = quiz.question
        first_answer.text = quiz.answer1
        second_answer.text = quiz.answer2
        third_answer.text = quiz.answer3
        fourth_answer.text = quiz.answer4
    }

    private fun handleAnswer(answerID: Int) {
        val quiz = quizs[currentQuizIndex]
        if (quiz.isCorrect(answerID)) {
            numOfGoodAnswers++
            Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "+0", Toast.LENGTH_SHORT).show()
        }

        //Pour aller à la question suivante
        currentQuizIndex++
         if(currentQuizIndex>=quizs.size) {

             var sharedPreferences = getSharedPreferences("com.epsi.myapplication", Context.MODE_PRIVATE)
             sharedPreferences.edit().putInt("userScore", numOfGoodAnswers).apply()

             var alert = AlertDialog.Builder(this)
             alert.setTitle("Partie terminée !")
             alert.setMessage("Vous avez eu : $numOfGoodAnswers bonne(s) réponse(s) sur ${quizs.size} \n $formatted")

             alert.setPositiveButton("OK") { dialogInterface: DialogInterface?, i: Int ->
                 finish()
             }
             alert.show()


         } else { //on continue la partie
             showQuestion(quizs[currentQuizIndex])
         }
    }

    fun onClickAnswerOne(view:View) {
        handleAnswer(1)
    }

    fun onClickAnswerTwo(view:View) {
        handleAnswer(2)
    }

    fun onClickAnswerThree(view:View) {
        handleAnswer(3)
    }

    fun onClickAnswerFour(view:View) {
        handleAnswer(4)
    }
}