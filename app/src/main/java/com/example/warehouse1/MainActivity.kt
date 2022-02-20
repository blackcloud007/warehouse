package com.example.warehouse1
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


// Import the required libraries


class MainActivity : AppCompatActivity() {
    // Create the object of TextView
    // and PieChart class
    var tvR: TextView? = null
    var tvPython: TextView? = null
    var tvCPP: TextView? = null
    var tvJava: TextView? = null
    var pieChart: PieChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link those objects with their
        // respective id's that
        // we have given in .XML file
        tvR = findViewById(R.id.tvR)
        tvPython = findViewById(R.id.tvPython)
        tvCPP = findViewById(R.id.tvCPP)
        tvJava = findViewById(R.id.tvJava)
        pieChart = findViewById(R.id.piechart)

        // Creating a method setData()
        // to set the text in text view and pie chart
        setData()
    }

    private fun setData() {

        // Set the percentage of language used
        tvR!!.text = 40.toString()
        tvPython!!.text = 30.toString()
        tvCPP!!.text = 5.toString()
        tvJava!!.text = 25.toString()

        // Set the data and color to the pie chart
        pieChart!!.addPieSlice(
            PieModel(
                "R", tvR!!.text.toString().toInt().toFloat(),
                Color.parseColor("#FFA726")
            )
        )
        pieChart!!.addPieSlice(
            PieModel(
                "Python", tvPython!!.text.toString().toInt().toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        pieChart!!.addPieSlice(
            PieModel(
                "C++", tvCPP!!.text.toString().toInt().toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        pieChart!!.addPieSlice(
            PieModel(
                "Java", tvJava!!.text.toString().toInt().toFloat(),
                Color.parseColor("#29B6F6")
            )
        )

        // To animate the pie chart
        pieChart!!.startAnimation()
    }
}
