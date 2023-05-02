package com.example.brewbuddyproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {

    private val TAG = "DetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val brewName = intent.getStringExtra("brewName")
        val brewStreet = intent.getStringExtra("brewStreet")
        val brewCity = intent.getStringExtra("brewCity")
        val brewPhone = intent.getStringExtra("brewPhone")
        val brewWebsite = intent.getStringExtra("brewWebsite")

        Log.d(TAG, "breweryName: $brewName")
        Log.d(TAG, "breweryStreet: $brewStreet")
        Log.d(TAG, "breweryCity: $brewCity")
        Log.d(TAG, "breweryPhone: $brewPhone")

        findViewById<TextView>(R.id.details_brewery_name).text = brewName.toString()
        findViewById<TextView>(R.id.details_brewery_addresss).text = brewStreet.toString() + ", " + brewCity.toString()
        findViewById<TextView>(R.id.details_brewery_phone_number).text = "Phone: " + brewPhone.toString()


    }

    fun websiteButton(view: View){
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("brewWebsite")))
        startActivity(i)
    }
}