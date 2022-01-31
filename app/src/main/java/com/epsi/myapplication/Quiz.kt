package com.epsi.myapplication

class Quiz(var question: String, var answer1: String, var answer2: String, var answer3: String, var answer4: String, var correctAnswerNum: Int) {

    fun isCorrect(answerNumber: Int) : Boolean {
        if(answerNumber == correctAnswerNum)
            return true

        return false
    }

}