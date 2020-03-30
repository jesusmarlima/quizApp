package com.jesusmar.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition:Int =  0
    private var mCorrectAnswer:Int = 0
    private var mUsername: String? = null
    private var mIsAlreadyAnswered = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        setQuestion()

        tvOpt1.setOnClickListener(this)
        tvOpt2.setOnClickListener(this)
        tvOpt3.setOnClickListener(this)
        tvOpt4.setOnClickListener(this)

        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.tvOpt1 -> {
                selectedOptionView(tvOpt1, 1)
            }
            R.id.tvOpt2 -> {
                selectedOptionView(tvOpt2, 2)
            }
            R.id.tvOpt3 -> {
                selectedOptionView(tvOpt3, 3)
            }
            R.id.tvOpt4 -> {
                selectedOptionView(tvOpt4, 4)
            }
            R.id.btnSubmit -> {
                submit()
            }
        }
    }

    private fun selectedOptionView(tv: TextView, SelectedOptionNumber: Int){
        if (mIsAlreadyAnswered) {
            return
        }
        defaultOptionView()
        mSelectedOptionPosition = SelectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selectd_option_border_bg)
    }

    private fun submit() {
        mIsAlreadyAnswered = true
        if (mSelectedOptionPosition == 0) {
            nextOrFinish()
        } else {
            evaluateAnswer()
        }
    }

    private fun setQuestion() {

        mIsAlreadyAnswered = false
        mQuestionList = Constants.getQuestions()

        defaultOptionView()
        setActionButtonLabel()

        val question: Question? = mQuestionList!![mCurrentPosition -1]

        progressBar.progress = mCurrentPosition
        tvProgress.text = "${mCurrentPosition}/${progressBar.max}"

        tvQuestion.text = question!!.question
        ivImage.setImageResource(question.image)
        tvOpt1.text = question.option1
        tvOpt2.text = question.option2
        tvOpt3.text = question.option3
        tvOpt4.text = question.option4
    }

    private fun defaultOptionView(){
        val opts = ArrayList<TextView>()
        opts.add(0,tvOpt1)
        opts.add(1,tvOpt2)
        opts.add(2,tvOpt3)
        opts.add(3,tvOpt4)

        for (opt in opts) {
            opt.setTextColor(Color.parseColor("#7A8089"))
            opt.typeface = Typeface.DEFAULT
            opt.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg)
        }

    }

    private fun setActionButtonLabel() {
        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit.text = "FINISH"
        } else if (mIsAlreadyAnswered){
            btnSubmit.text  = "GO TO THE NEXT QUESTION"
        } else {
            btnSubmit.text = "SUBMIT"
        }
    }

    private fun evaluateAnswer() {
        val question = mQuestionList?.get(mCurrentPosition - 1)
        if (question?.correctAnswer != mSelectedOptionPosition) {
            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
        } else {
            mCorrectAnswer++
        }
        answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)
        setActionButtonLabel()
        mSelectedOptionPosition = 0
    }

    private fun answerView(answer:Int, drawableView:Int){
        when (answer){
            1 -> tvOpt1.background = ContextCompat.getDrawable(this,drawableView)
            2 -> tvOpt2.background = ContextCompat.getDrawable(this,drawableView)
            3 -> tvOpt3.background = ContextCompat.getDrawable(this,drawableView)
            4 -> tvOpt4.background = ContextCompat.getDrawable(this,drawableView)
        }
    }

    private fun nextOrFinish() {
        mCurrentPosition++
        if (mCurrentPosition <= mQuestionList!!.size) setQuestion() else callResultView()
    }

    private fun callResultView() {
        val resultIntent = Intent(this, ResultActivity::class.java)
        resultIntent.putExtra(Constants.USER_NAME, mUsername)
        resultIntent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswer)
        resultIntent.putExtra(Constants.TOTAL_QWUESTIONS, mQuestionList!!.size)
        startActivity(resultIntent)
        finish()
    }

}
