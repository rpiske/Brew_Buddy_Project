package com.example.brewbuddyproject

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyRecycleAdapter(private val breweryLocations: ArrayList<Brewery>, private val context: Context): RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>() {

    var counter = 1
    var selectedItemPosition: Int = -1 // -1 means that nothing was selected

    private val TAG = "MyRecycleAdapter"


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val brewName = itemView.findViewById<TextView>(R.id.brew_name)
        val brewStreet = itemView.findViewById<TextView>(R.id.brew_street)
        val brewCity = itemView.findViewById<TextView>(R.id.brew_city_state)
        val brewPhone = itemView.findViewById<TextView>(R.id.brew_phone)
        val brewWebsite = itemView.findViewById<TextView>(R.id.brew_website)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: ${counter++}")


        return MyViewHolder(view)


    }

    override fun getItemCount(): Int {
        return breweryLocations.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.brewName.text = breweryLocations[position].name
        holder.brewStreet.text = breweryLocations[position].street
        holder.brewCity.text = "${breweryLocations[position].city}, ${breweryLocations[position].state} ${breweryLocations[position].zip}"
        holder.brewPhone.text = breweryLocations[position].phone
        holder.brewWebsite.text = breweryLocations[position].website_url




        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()

            val myIntent = Intent(context, DetailsActivity::class.java)

            myIntent.putExtra("brewName", holder.brewName.text)
            myIntent.putExtra("brewStreet", holder.brewStreet.text)
            myIntent.putExtra("brewCity", holder.brewCity.text)
            myIntent.putExtra("brewPhone", holder.brewPhone.text)
            myIntent.putExtra("brewWebsite", holder.brewWebsite.text)
            myIntent.putExtra("position", selectedItemPosition)
            val brewery = breweryLocations[selectedItemPosition]

            myIntent.putExtra("passedBrewery", brewery)

            context.startActivity(myIntent)

        }


        if(selectedItemPosition == position){
            // Change Current Selection to Orange
            holder.itemView.setBackgroundColor(Color.parseColor("#f28e1c"))

        }
        else
        {
            // All other items will be changed back to a default of White
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))

        }
    }
    // Return the name of the current brewery that is selected
    fun getCurrentBrewerySelection() : Int {
        return selectedItemPosition
    }


}