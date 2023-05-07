package com.example.brewbuddyproject

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyCommentsAdapter(private val breweryComments: ArrayList<Comments>, private val context: Context): RecyclerView.Adapter<MyCommentsAdapter.MyCommentsViewHolder>() {

    var counter = 1
    var selectedItemPosition: Int = -1 //-1 default: nothing selected
    private val TAG = "MyCommentsAdapter"

    inner class MyCommentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val brewName = itemView.findViewById<TextView>(R.id.comment_user_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        Log.d(TAG, "onCreateCOMMENTViewHolder: ${counter++}")
        return MyCommentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return breweryComments.size
    }

    override fun onBindViewHolder(holder: MyCommentsViewHolder, position: Int) {
        holder.brewName.text = "${breweryComments[position].user}: \"${breweryComments[position].comment}\""
    }
}