package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class TrainActivity : AppCompatActivity() {

    private val exercises = ExerciseDataBase.exercises
    private var exerciseIndex = 0

    private lateinit var toolbarTrain: Toolbar

    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    private lateinit var startButtonBTN: Button
    private lateinit var titleTV: TextView
    private lateinit var exerciseTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var timerTV: TextView
    private lateinit var completedButtonBTN: Button
    private lateinit var imageViewIV: ImageView
    private lateinit var returnButtonBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_train)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarTrain = findViewById(R.id.toolbarTrain)
        setSupportActionBar(toolbarTrain)
        title="Тренировки по фитнесу"

        startButtonBTN = findViewById(R.id.startButtonBTN)
        titleTV = findViewById(R.id.titleTV)
        exerciseTV = findViewById(R.id.exerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        timerTV = findViewById(R.id.timerTV)
        completedButtonBTN = findViewById(R.id.completedButtonBTN)
        imageViewIV = findViewById(R.id.imageViewIV)
        returnButtonBTN = findViewById(R.id.returnButtonBTN)

        exerciseIndex = intent.getIntExtra("exerciseIndex", 0)

        startButtonBTN.setOnClickListener {
            startWorkout()
        }

        completedButtonBTN.setOnClickListener {
            completedExercise()
        }

        returnButtonBTN.setOnClickListener {
            val intent = Intent(this, ExerciseChoose::class.java)
            startActivity(intent)
        }
    }

    private fun startWorkout() {
        titleTV.text = "Начало тренировки"
        startButtonBTN.isEnabled = false
        startButtonBTN.text = "Процесс тренировки"
        returnButtonBTN.isVisible = false
        startNextExercise()
    }

    private fun completedExercise() {
        timer.cancel()
        completedButtonBTN.isEnabled = false
        startNextExercise()
    }

    private fun startNextExercise() {
        if (exerciseIndex < exercises.size) {
            currentExercise = exercises[exerciseIndex]
            exerciseTV.text = currentExercise.name
            descriptionTV.text = currentExercise.description
            imageViewIV.setImageResource(exercises[exerciseIndex].gifImage)
            timerTV.text = formatTime(currentExercise.durationInSeconds)
            timer = object : CountDownTimer(currentExercise.durationInSeconds * 1000L, 1000)
            {
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = "Упражнение завершено"
                    imageViewIV.visibility = View.VISIBLE
                    completedButtonBTN.isEnabled = true
                    returnButtonBTN.isVisible = true
                    imageViewIV.setImageResource(0)
                }
            }.start()
            exerciseIndex++
        } else {
            exerciseTV.text = "Тренировка завершена"
            descriptionTV.text = ""
            timerTV.text = ""
            completedButtonBTN.isEnabled = false
            startButtonBTN.isEnabled = true
            startButtonBTN.text = "Начать снова"
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.mainExit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}