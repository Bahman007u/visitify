package com.example.shopify.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopify.R
import com.example.shopify.adapters.TripsAdapter
import com.example.shopify.databinding.ActivityResult2Binding
import com.example.shopify.model.Trip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ResultActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityResult2Binding
    private lateinit var from:String
    private lateinit var to:String
    private lateinit var date:String
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityResult2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        database=FirebaseDatabase.getInstance()
        getIntentExtra()
        setVariable()
        initList()

    }

    private fun initList() {
        val myRef:DatabaseReference=database!!.getReference("Trips")
        val list=ArrayList<Trip>()
        val query:Query=myRef.orderByChild("from").equalTo(from)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(issue in snapshot.children){
                        val trip=issue.getValue(Trip::class.java)
                        trip?.let{
                            if(it.to==to){
                                list.add(it)
                            }
                        }
                    }
                    if(list.isNotEmpty()){
                        binding.searchView.layoutManager=LinearLayoutManager(this@ResultActivity2,
                            LinearLayoutManager.VERTICAL,
                            false)
                        binding.searchView.adapter=TripsAdapter(list)
                    }
                    binding.progressBar.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        } )
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener{finish()}
    }

    private fun getIntentExtra() {
        from=intent.getStringExtra("from")?:""
        to=intent.getStringExtra("to")?:""
        date=intent.getStringExtra("date")?:""


    }
}