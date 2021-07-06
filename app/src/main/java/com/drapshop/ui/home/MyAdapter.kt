package com.drapshop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drapshop.R
import com.drapshop.data.Piece
import de.hdodenhof.circleimageview.CircleImageView

class MyAdapter(var dataSet: ArrayList<Piece>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTV: TextView
        val priceTV: TextView
        val photoIV: CircleImageView

        init {
            // Define click listener for the ViewHolder's View.
            titleTV = view.findViewById(R.id.title_tv)
            priceTV = view.findViewById(R.id.price_tv)
            photoIV = view.findViewById(R.id.photo_iv)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.titleTV.text = dataSet[position].title
        viewHolder.priceTV.text = "Price: $"+dataSet[position].price.toString()

        Glide.with(viewHolder.photoIV.context)
            .load(dataSet[position].photo_url)
            .into(viewHolder.photoIV)

    }

    override fun getItemCount() = dataSet.size
}