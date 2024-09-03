package com.example.it_quiz

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifDrawable

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResultTitle: TextView
    private lateinit var ivResultEmoji: ImageView
    private lateinit var tvCorrectAnswers: TextView
    private lateinit var tvIncorrectAnswers: TextView
    private lateinit var tvSkippedQuestions: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvRetry: TextView
    private lateinit var tvShare: TextView
    private lateinit var tvRate: TextView
    private lateinit var tvHome: TextView

    private var correctAnswersCount = 0
    private var incorrectAnswersCount = 0
    private var skippedQuestionsCount = 0
    private var scoreCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Инициализация виджетов
        tvResultTitle = findViewById(R.id.tvResultTitle)
        ivResultEmoji = findViewById(R.id.ivResultEmoji)
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers)
        tvIncorrectAnswers = findViewById(R.id.tvIncorrectAnswers)
        tvSkippedQuestions = findViewById(R.id.tvSkippedQuestions)
        tvScore = findViewById(R.id.tvScore)
        tvRetry = findViewById(R.id.tvRetry)
        tvShare = findViewById(R.id.tvShare)
        tvRate = findViewById(R.id.tvRate)
        tvHome = findViewById(R.id.tvHome)

        // Получение значений из Intent
        correctAnswersCount = intent.getIntExtra("CORRECT_ANSWERS", 0)
        incorrectAnswersCount = intent.getIntExtra("INCORRECT_ANSWERS", 0)
        skippedQuestionsCount = intent.getIntExtra("SKIPPED_QUESTIONS", 0)
        scoreCount = intent.getIntExtra("SCORE", 0)

        updateResultViews()

        if (correctAnswersCount > 6) {
            val gifFromResource = GifDrawable(resources, R.drawable.smiley_face)
            ivResultEmoji.setImageDrawable(gifFromResource)
            Toast.makeText(this, "Супер! Так держать!", Toast.LENGTH_LONG).show()
        } else {
            val gifFromResource = GifDrawable(resources, R.drawable.sad_face)
            ivResultEmoji.setImageDrawable(gifFromResource)
            Toast.makeText(this, "Ты можешь лучше! Дерзай!", Toast.LENGTH_LONG).show()
        }

        tvRetry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvShare.setOnClickListener {
            // Логика для поделиться счетом
        }

        tvRate.setOnClickListener {
            // Логика для оценки приложения
        }

        tvHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateResultViews() {
        tvCorrectAnswers.text = getString(R.string.correct_answers_text, correctAnswersCount)
        tvIncorrectAnswers.text = getString(R.string.incorrect_answers_text, incorrectAnswersCount)
        tvSkippedQuestions.text = getString(R.string.skipped_questions_text, skippedQuestionsCount)
        tvScore.text = getString(R.string.score_text, scoreCount)
    }
}