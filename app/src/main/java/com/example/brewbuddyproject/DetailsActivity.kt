package com.example.brewbuddyproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore

class DetailsActivity : AppCompatActivity() {

    // Globals
    private val TAG = "DetailsActivity"
    private lateinit var myRecycleAdapter: MyRecycleAdapter
    private lateinit var fireBasedb: FirebaseFirestore
    private  var recyclerPosition : Int = 0
    private var passedBrewery : Brewery? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val brewName = intent.getStringExtra("brewName")
        val brewStreet = intent.getStringExtra("brewStreet")
        val brewCity = intent.getStringExtra("brewCity")
        val brewPhone = intent.getStringExtra("brewPhone")
        val brewWebsite = intent.getStringExtra("brewWebsite")

        passedBrewery = intent.getSerializableExtra("passedBrewery") as? Brewery


        // Get the Cloud firestore Instance
        fireBasedb = FirebaseFirestore.getInstance()

      //  val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
     //   myRecycleAdapter = MyRecycleAdapter(breweryLocations, this)
       // recyclerView.adapter = myRecycleAdapter


        Log.d(TAG, "breweryName: $brewName")
        Log.d(TAG, "breweryStreet: $brewStreet")
        Log.d(TAG, "breweryCity: $brewCity")
        Log.d(TAG, "breweryPhone: $brewPhone")

        findViewById<TextView>(R.id.details_brewery_name).text = brewName.toString()
        findViewById<TextView>(R.id.details_brewery_addresss).text = brewStreet.toString() + ", " + brewCity.toString() + "," + brewWebsite.toString()
        findViewById<TextView>(R.id.details_brewery_phone_number).text = "Phone: " + brewPhone.toString()
    }

    fun websiteButton(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(intent.getStringExtra("brewWebsite"))
        startActivity(browserIntent)
    }

    fun phoneNumberCall(view: View) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + intent.getStringExtra("brewPhone"))
        startActivity(callIntent)
    }


    fun addBreweryButton(view: View){
        // Get the last position in the Brewery List that was last selected
        val position = recyclerPosition



        if(position != -1){
            addBrewery(passedBrewery)
        }
        else
        {
            showDialog("Error", "Please make a selection by clicking a brewery")
        }


    }

    // Passing a Brewery and adding it to the database
    private fun addBrewery(passedBrewery : Brewery?){


        // Getting an instance of our collection
        val breweryDatabase = fireBasedb.collection("breweries")

        // Getting the auto generated id for the document that we want to create
        val documentId = breweryDatabase.document().id


        // Adding the data
        if (passedBrewery != null) {
            breweryDatabase.document(documentId).set(passedBrewery)
        }

        if (passedBrewery != null) {
            showDialog("Success", "${passedBrewery.name} Brewery has been added.")
        }

    }

    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }
}