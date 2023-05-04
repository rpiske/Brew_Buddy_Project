package com.example.brewbuddyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var fireBasedb: FirebaseFirestore
    // https://console.firebase.google.com/u/0/ - Database Link


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Get the Cloud firestore Instance
        fireBasedb = FirebaseFirestore.getInstance()


        // On initial creation, we want to go directly to the login screen
        // If the user is not currently logged in, the login screen will take us directly
        // to the registration screen. Where the user can log in or create an authentication.

        val currentUser = FirebaseAuth.getInstance().currentUser

        if(currentUser == null) {
            val myIntent = Intent(this, ActivityLogin::class.java)
            startActivity(myIntent)

            finish()
        }

        else{


        findViewById<Button>(R.id.search_breweries).setOnClickListener {

            val myIntent = Intent(this, ResultsActivity::class.java)
            startActivity(myIntent)
         }
        }
    }

    // Open the new Activity so the user can delete a brewery
    fun deleteButton(view: View){
        val myIntent = Intent(this, DeleteBreweryActivity::class.java)
        startActivity(myIntent)
    }

    // View a record of all the Breweries
    fun viewFavorites(view: View){
        val myIntent = Intent(this, FavoritesActivity::class.java)
        startActivity(myIntent)
    }


    /*private fun viewAllBreweries(){

        // Retrieve data
        fireBasedb.collection("breweries")
            .orderBy("name")
            .get()
            .addOnSuccessListener { documents ->

                val buffer = StringBuffer()

                for(document in documents){
                    if (document.get("user") == getCurrentUser()) {
                        Log.d(TAG, "${document.id} => ${document.data}")

                        Log.d(
                            TAG,
                            "Brewery: ${document.get("name")}, ${document.get("street")}, ${
                                document.get("city")
                            }," +
                                    "${document.get("state")}, ${document.get("zip")}, ${
                                        document.get(
                                            "phone"
                                        )
                                    }, " +
                                    "${document.get("website_url")}  "
                        )
                        *//*val tempBrewery = Brewery("${document.get("name")}",
                            "${document.get("street")}",
                            "${document.get("city")}",
                            "${document.get("state")}",
                            "${document.get("zip")}",
                            "null",
                            "null",
                            "${document.get("phone")}",
                            "${document.get("website_url")}",
                            "${document.get("brewery_type")}",
                            "${document.get("user")}",
                            0.0F,
                            "${document.get("comments")}")

                            favoriteLocations.add(tempBrewery)
                        *//*
                        buffer.append("Name : ${document.get("name")}" + "\n")
                        buffer.append("Street : ${document.get("street")}" + "\n")
                        buffer.append("City : ${document.get("city")}" + "\n")
                        buffer.append("State : ${document.get("state")}" + "\n")
                        buffer.append("Zip : ${document.get("zip")}" + "\n")
                        buffer.append("Phone : ${document.get("phone")}" + "\n")
                        buffer.append("Website : ${document.get("website_url")}" + "\n\n")
                    }
                }
                // Show the listing of breweries
                showDialog("Brewery Listing: ", buffer.toString())
            }
            .addOnFailureListener{
                Log.d(TAG, "Error getting documents")
                showDialog("Error", "Error getting breweries")
            }
    }*/
    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.setPositiveButton("OK"){ dialog, which ->

        }
        builder.show()
    }



    // Create the menu option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle when the menu options log out is selected, if it's selected - log the user out
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.action_logout->{

                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener{task->

                        // If the logout button is clicked, logout the user
                        if(task.isSuccessful){
                            // Call the splash screen and the splash screen now calls the register
                            animatedSplashScreen()
                           // startRegisterActivity() They need to register/log back in
                        }
                        else
                        {
                            Log.e(TAG, "Task is not successful: ${task.exception}")
                        }
                    }
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun animatedSplashScreen() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)

        finish()
    }

    // Return the current users email
    private fun getCurrentUser() : String? {
        return Firebase.auth.currentUser?.email.toString()
    }

}


