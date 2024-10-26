package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExerciseChoose : AppCompatActivity() {

    private val exercises = ExerciseDataBase.exercises
    private var exerciseIndex = 0

    private lateinit var toolbarChoose: Toolbar
    private lateinit var linearLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercise_choose)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarChoose = findViewById(R.id.toolbarChoose)
        setSupportActionBar(toolbarChoose)
        title="Тренировки по фитнесу"

        linearLayout = findViewById(R.id.LL)

        val clickListener = View.OnClickListener { view ->
            val textView = view as TextView
            exerciseIndex = textView.tag as Int
            val intent = Intent(this, TrainActivity::class.java)
            intent.putExtra("exerciseIndex", exerciseIndex)
            startActivity(intent)
        }

        for (i in exercises.indices) {
            val textView = TextView(this)
            textView.text = exercises[i].name
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.tag = i
            textView.setOnClickListener(clickListener)
            textView.textSize = 20f
            textView.setTypeface(null, Typeface.BOLD)
            val params = textView.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0, 5,0,0)
            textView.layoutParams = params
            linearLayout.addView(textView)
        }
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