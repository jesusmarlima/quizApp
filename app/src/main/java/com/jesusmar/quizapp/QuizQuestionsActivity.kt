package com.jesusmar.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        val questionList = Constants.getQuestions();

        val currentPosition  = 1
        val quesiton: Question? = questionList[currentPosition -1]

        progressBar.progress = currentPosition
        tvProgress.text = "${currentPosition}/${progressBar.max}"

        tvQuestion.text = quesiton!!.question
        ivImage.setImageResource(quesiton.image)
        tvOpt1.text = quesiton.option1
        tvOpt2.text = quesiton.option2
        tvOpt3.text = quesiton.option3
        tvOpt4.text = quesiton.option4

    }
}
