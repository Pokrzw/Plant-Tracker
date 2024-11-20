package com.example.plantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(
    private val plants: MutableList<Plant>,
    private val onDeleteClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.plantNameText)
        val speciesText: TextView = itemView.findViewById(R.id.plantSpeciesText)
        val descriptionText: TextView = itemView.findViewById(R.id.plantDescriptionText)
        val deleteButton: View = itemView.findViewById(R.id.deleteButton) // Added deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.nameText.text = plant.name
        holder.speciesText.text = plant.species
        holder.descriptionText.apply {
            text = plant.description
            visibility = if (plant.description.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(plant)
        }
    }

    override fun getItemCount() = plants.size

    fun deletePlant(plant: Plant) {
        plants.remove(plant)
        notifyDataSetChanged()
    }
}
