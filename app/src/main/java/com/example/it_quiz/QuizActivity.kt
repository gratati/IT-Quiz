package com.example.it_quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private var questionIndex = 0
    private var score = 0
    private val questionBank = QuestionBank()
    private val questions = questionBank.getQuestions()
    private var timer: CountDownTimer? = null
    private var remainingTime: Long = 20000
    private var correctAnswersCount = 0
    private var incorrectAnswersCount = 0
    private var skippedQuestionsCount = 0

    // Виджеты
    private lateinit var tvAppName: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvTimer: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbOptionA: RadioButton
    private lateinit var rbOptionB: RadioButton
    private lateinit var rbOptionC: RadioButton
    private lateinit var rbOptionD: RadioButton
    private lateinit var btnNextQuestion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Инициализация виджетов
        tvAppName = findViewById(R.id.tvAppName)
        tvScore = findViewById(R.id.tvScore)
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvTimer = findViewById(R.id.tvTimer)
        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        rbOptionA = findViewById(R.id.rbOptionA)
        rbOptionB = findViewById(R.id.rbOptionB)
        rbOptionC = findViewById(R.id.rbOptionC)
        rbOptionD = findViewById(R.id.rbOptionD)
        btnNextQuestion = findViewById(R.id.btnNextQuestion)

        loadQuestion()

        btnNextQuestion.setOnClickListener {
            handleNextQuestion()
        }

        startTimer()
    }

    private fun loadQuestion() {
        val currentQuestion = questions[questionIndex]
        tvQuestion.text = currentQuestion.question
        rbOptionA.text = "A. " + currentQuestion.optionA
        rbOptionB.text = "B. " + currentQuestion.optionB
        rbOptionC.text = "C. " + currentQuestion.optionC
        rbOptionD.text = "D. " + currentQuestion.optionD
        tvQuestionNumber.text = "${questionIndex + 1}/${questions.size}"
    }

    private fun startTimer() {
        timer?.cancel() // Отменить любой существующий таймер
        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                tvTimer.text = "Время: ${millisUntilFinished / 1000}"
                if (millisUntilFinished / 1000 <= 10) {
                    tvTimer.setTextColor(getColor(android.R.color.holo_red_dark))
                }
            }

            override fun onFinish() {
                showTimeUpDialog()
            }
        }.start()
    }

    private fun handleNextQuestion() {
        timer?.cancel()
        if (rgOptions.checkedRadioButtonId == -1) {
            skippedQuestionsCount++
            showSkippedQuestionDialog()
        } else {
            val selectedOption = when (rgOptions.checkedRadioButtonId) {
                R.id.rbOptionA -> "A"
                R.id.rbOptionB -> "B"
                R.id.rbOptionC -> "C"
                R.id.rbOptionD -> "D"
                else -> ""
            }

            val correctOption = questions[questionIndex].correctOption
            if (selectedOption == correctOption) {
                correctAnswersCount++
                score++
                showCorrectAnswerDialog()
            } else {
                incorrectAnswersCount++
                showIncorrectAnswerDialog(correctOption)
            }
        }
    }

    private fun showCorrectAnswerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_correct)
        val view = layoutInflater.inflate(R.layout.dialog_correct, null)
        val tvScore: TextView = view.findViewById(R.id.tvScore)
        tvScore.text = "Счет: $score"

        builder.setView(view)
        val alertDialog = builder.create()

        // Обработчик нажатия кнопки "OK"
        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            alertDialog.dismiss()
            nextQuestion() // Переход на следующий вопрос
        }

        alertDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showIncorrectAnswerDialog(correctOption: String) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_incorrect)

        val view = layoutInflater.inflate(R.layout.dialog_incorrect, null)
        builder.setView(view)

        val titleTextView = view.findViewById<TextView>(R.id.tvIncorrectAnswers)
        titleTextView.text = "Неправильный ответ"

        val messageTextView = view.findViewById<TextView>(R.id.tvCorrectAnswerDialog)
        messageTextView.text = "Правильный ответ: $correctOption"

        val alertDialog = builder.create()

        // Обработчик нажатия кнопки "OK"
        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            alertDialog.dismiss()
            nextQuestion() // Переход на следующий вопрос
        }

        alertDialog.show()
    }

    private fun showTimeUpDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_time_up)

        val view = layoutInflater.inflate(R.layout.dialog_time_up, null)
        builder.setView(view)

        val alertDialog = builder.create()

        // Обработчик нажатия кнопки "OK"
        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            alertDialog.dismiss()
            nextQuestion() // Переход на следующий вопрос
        }

        alertDialog.show()
    }

    private fun showSkippedQuestionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Пропуск вопроса")
        builder.setMessage("Вы пропустили вопрос.")
        builder.setIcon(R.drawable.icon_time_up)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            nextQuestion() // Переход на следующий вопрос
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun nextQuestion() {
        questionIndex++
        if (questionIndex < questions.size) {
            reset()
            loadQuestion()
            startTimer()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("CORRECT_ANSWERS", correctAnswersCount)
            intent.putExtra("INCORRECT_ANSWERS", incorrectAnswersCount)
            intent.putExtra("SKIPPED_QUESTIONS", skippedQuestionsCount)
            intent.putExtra("SCORE", score)
            intent.putExtra("TOTAL_QUESTIONS", questions.size)
            startActivity(intent)
            finish()
        }
    }

    private fun reset() {
        rgOptions.clearCheck()
        remainingTime = 30000
        tvTimer.setTextColor(getColor(android.R.color.black))
    }
}