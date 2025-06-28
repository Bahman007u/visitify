package com.example.shopify.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopify.R
import com.example.shopify.databinding.ActivitySearch2Binding
import com.example.shopify.model.Location
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySearch2Binding
    private var adultPassenger = 1
    private val dateFormat = SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH)
    private val calendar = Calendar.getInstance()
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Initialize ViewBinding
        binding = ActivitySearch2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initLocation()
        initPassenger()
        initDatePickup()
        setVariable()

    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener{finish()}
            category=intent.getStringExtra("cat")!!
            when(category){
                "1"->{
                    logoimg.setImageResource(R.drawable.train)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_green)
                }
                "2"->{
                    logoimg.setImageResource(R.drawable.airplane)
                    mainLayout.setBackgroundResource(R.drawable.gradiant_bg_blue)
                }
                "3"->{
                    logoimg.setImageResource(R.drawable.boat)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_purple)
                }
                "4"->{
                    logoimg.setImageResource(R.drawable.bus)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_orange)
                }
            }
            searchBtn.setOnClickListener{
                val intent=Intent(this@SearchActivity2, ResultActivity2::class.java)
                intent.putExtra("from", (binding.fromSp.selectedItem as Location).Name)
                intent.putExtra("to", (binding.toSp.selectedItem as Location).Name)
                intent.putExtra("date", binding.departureDateTxt.text.toString())
                intent.putExtra("numPassenger", adultPassenger)
                startActivity(intent)
            }
        }
    }

    private fun initDatePickup() {
        val today=Calendar.getInstance()
        val tommorow=Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR,1) }
        binding.apply {
            departureDateTxt.text=dateFormat.format(today.time)
            returnDateTxt.text=dateFormat.format(tommorow.time)
            departureDateTxt.setOnClickListener{showDatePickerDialog(departureDateTxt)}
            returnDateTxt.setOnClickListener{showDatePickerDialog(returnDateTxt)}
        }
    }

    private fun showDatePickerDialog(text: TextView) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_YEAR)
        DatePickerDialog(this, { _, selectedYear,selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            text.text = dateFormat.format(calendar.time)
        }, year, month, day).show()

    }

    private fun initPassenger() {
        binding.apply {
            plusAdultBtn.setOnClickListener{
                adultPassenger++
                adultTxt.text="$adultPassenger Adult"
            }
            minusAdultBtn.setOnClickListener{
                if(adultPassenger>1){
                    adultPassenger--
                    adultTxt.text="$adultPassenger Adult"
                }
            }
        }
    }

    private fun initLocation() {
        binding.apply {
            progressBarFrom.visibility = View.VISIBLE
            progressBarTo.visibility = View.VISIBLE

            val myRef: DatabaseReference = database.getReference("Locations")
            val list = ArrayList<Location>()

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (issue in snapshot.children) {
                            issue.getValue(Location::class.java)?.let { list.add(it) }
                        }
                        Log.d("SpinnerData", "Loaded items: ${list.size} - $list")
                        val adapter = ArrayAdapter(this@SearchActivity2, R.layout.sp_item, list).also {
                            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // ✅ correct
                        }
                        fromSp.adapter = adapter
                        toSp.adapter = adapter
                        fromSp.setSelection(1)
                        progressBarFrom.visibility = View.GONE
                        progressBarTo.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error properly instead of TODO
                }
            })
        }
    }
}
