package com.example.shopify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopify.databinding.ViewHolderRipBinding
import com.example.shopify.model.Trip

class TripsAdapter(private val trips: List<Trip>) :
    RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewHolderRipBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewHolderRipBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]
        Glide.with(context)
            .load(trip.companyLogo)
            .into(holder.binding.logo)

        holder.binding.companyTxt.text = trip.companyName
        holder.binding.fromTxt.text = trip.from
        holder.binding.fromSmartTxt.text = trip.fromShort
        holder.binding.toTxt.text = trip.to
        holder.binding.toSmartTxt.text = trip.toShort
        holder.binding.arriveTxt.text = trip.arriveTime
        holder.binding.scoreTxt.text = trip.score.toString()
        holder.binding.priceTxt.text = "\$${trip.price}/per"
    }

    override fun getItemCount(): Int = trips.size
}
