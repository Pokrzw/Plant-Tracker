package com.example.plantapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val plantList = mutableListOf<Plant>()
    private lateinit var plantAdapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.plantRecyclerView)
        val addPlantButton: View = findViewById(R.id.addPlantButton)

        plantAdapter = PlantAdapter(plantList) { plant ->
            deletePlant(plant)
        }
        recyclerView.adapter = plantAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addPlantButton.setOnClickListener {
            showAddPlantDialog()
        }
    }

    private fun showAddPlantDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_plant, null)
        val nameInput: TextInputEditText = dialogView.findViewById(R.id.plantNameInput)
        val speciesInput: TextInputEditText = dialogView.findViewById(R.id.plantSpeciesInput)
        val descriptionInput: TextInputEditText = dialogView.findViewById(R.id.plantDescriptionInput)

        AlertDialog.Builder(this)
            .setTitle("Add Plant")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString().trim()
                val species = speciesInput.text.toString().trim()
                val description = descriptionInput.text.toString().trim()

                if (name.isNotEmpty() && species.isNotEmpty()) {
                    plantList.add(Plant(name, species, description))
                    plantAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deletePlant(plant: Plant) {
        AlertDialog.Builder(this)
            .setMessage("Delete ${plant.name}?")
            .setPositiveButton("Yes") { _, _ ->
                plantAdapter.deletePlant(plant)
            }
            .setNegativeButton("No", null)
            .show()
    }
}