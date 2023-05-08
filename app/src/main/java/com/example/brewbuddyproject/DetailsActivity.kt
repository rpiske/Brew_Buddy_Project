package com.example.brewbuddyproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DetailsActivity : AppCompatActivity() {

    // Globals
    private val TAG = "DetailsActivity"
    private lateinit var myCommentsRecyclerAdapter: MyCommentsAdapter
    private lateinit var fireBaseDB: FirebaseFirestore
    private var recyclerPosition : Int = 0
    private var currentUser: String? = null
    private var passedBrewery : Brewery? = null
    private var breweryComments = ArrayList<Comments>()
    private var breweryNumberOfComments = 0
    private var foundID: String? = null

    private var breweryRating: Float = 0.0F
    private var breweryNumberOfRatings: Int = 0
    private var hasUserRated: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        passedBrewery = intent.getSerializableExtra("passedBrewery") as? Brewery

        // Get the Cloud firestore Instance
        fireBaseDB = FirebaseFirestore.getInstance()

        //Load all base brewery details
        findViewById<TextView>(R.id.details_brewery_name).text = passedBrewery?.name.toString()
        findViewById<TextView>(R.id.details_brewery_addresss).text = "${passedBrewery?.street}, ${passedBrewery?.city} ${passedBrewery?.state}"
        findViewById<TextView>(R.id.details_brewery_phone_number).text = "Phone: ${passedBrewery?.phone}"
        findViewById<TextView>(R.id.details_brewery_type).text = passedBrewery?.brewery_type


        val commentRecyclerView = findViewById<RecyclerView>(R.id.details_comment_recycler)
        myCommentsRecyclerAdapter = MyCommentsAdapter(breweryComments, this)

        commentRecyclerView.adapter = myCommentsRecyclerAdapter
        commentRecyclerView.layoutManager = LinearLayoutManager(this)
        commentRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        pullCommentsFromDB()
        pullRatingsFromDB()

        //check if brewery is already in DB
        fireBaseDB.collection("breweries")
            .orderBy("name")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    if (document.get("name") != null) {
                        if (document.get("name") == passedBrewery?.name) {
                            Log.d(TAG, "fireBaseDB: found matching brewery at ${document.id}")
                            foundID = document.id
                        }
                    }
                }
            }
    }

    fun pullRatingsFromDB() {
        breweryRating = 0.0F
        breweryNumberOfRatings = 0
        fireBaseDB.collection("ratings")
            .orderBy("name")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.get("name") != null) {
                        if (document.get("name") == passedBrewery?.name) {
                            val tempFloat: Double? = document.getDouble("rating")
                            breweryRating += tempFloat?.toFloat()!!
                            breweryNumberOfRatings++
                            if(document.get("user") == getCurrentUser())
                                hasUserRated = true
                        }
                    }
                }
                if(breweryNumberOfRatings != 0)
                    findViewById<RatingBar>(R.id.details_brewery_rating).rating = breweryRating/breweryNumberOfRatings
                findViewById<TextView>(R.id.details_brewery_num_ratings).text = "($breweryNumberOfRatings Ratings)"
            }
    }

    fun pullCommentsFromDB() {
        breweryNumberOfComments = 0
        fireBaseDB.collection("comments")
            .orderBy("commentNumber")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    if(document.get("name") != null) {
                        if(document.get("name") == passedBrewery?.name) {
                            breweryComments?.add(Comments("${document.get("name")}", "${document.get("user")}", "${document.get("comment")}", "${document.get("commentNumber")}"))
                            breweryNumberOfComments++
                        }
                    }
                }
                Log.d(TAG, "pullCommentsFromDB: numberComments: $breweryNumberOfComments")
                myCommentsRecyclerAdapter.notifyDataSetChanged()
            }
    }

    fun commentButton(view: View) {
        val commentsDB = fireBaseDB.collection("comments")
        val tempNumComments:String = (breweryNumberOfComments++).toString()
        Log.d(TAG, "commentButton: tempNumComments: $tempNumComments")
        commentsDB.document(commentsDB.document().id).set(Comments("${passedBrewery?.name}", "${getCurrentUser()}", "${findViewById<TextView>(R.id.details_enter_comment_textbox).text}", tempNumComments))
        breweryComments.clear()
        findViewById<TextView>(R.id.details_enter_comment_textbox).text = ""
        view.hideKeyboard()
        pullCommentsFromDB()
    }

    fun ratingButton(view: View) {
        val ratingsDB = fireBaseDB.collection("ratings")
        if(!hasUserRated) {
            ratingsDB.document(ratingsDB.document().id).set(Ratings("${passedBrewery?.name}", "${getCurrentUser()}", findViewById<RatingBar>(R.id.details_brewery_rating).rating))
        } else Toast.makeText(this, "You've already rated this brewery!", Toast.LENGTH_SHORT).show()
        pullRatingsFromDB()
    }

    fun backButton(view: View) {
        Log.d(TAG, "backButton: inside")
        finish()
    }

    //Opens web view activity
    fun websiteButton(view: View){
        val myIntent = Intent(this, WebActivity::class.java)

        myIntent.putExtra("brewWebsite", passedBrewery?.website_url)
        this.startActivity(myIntent)
    }

    //Opens the camera activity
    fun openCamera(view: View){
        val myIntent = Intent(this, CameraActivity::class.java)
        this.startActivity(myIntent)
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

        // Attach the curent users email address to the dataobject
        if (passedBrewery != null) {
            if(foundID == null) {
                Log.d(TAG, "addBrewery: first stop")
                passedBrewery.user = getCurrentUser() // Add this to the Data Object
                // Getting an instance of our collection
                val breweryDatabase = fireBaseDB.collection("breweries")

                // Getting the auto generated id for the document that we want to create
                val documentId = breweryDatabase.document().id
                foundID = documentId
                breweryDatabase.document(documentId).set(passedBrewery)
                showDialog("Success", "${passedBrewery.name} Brewery has been added.")
            } else Toast.makeText(this, "Brewery already in favorites!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    // Return the current users email
    private fun getCurrentUser() : String? {
        return Firebase.auth.currentUser?.email.toString()
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
    
}