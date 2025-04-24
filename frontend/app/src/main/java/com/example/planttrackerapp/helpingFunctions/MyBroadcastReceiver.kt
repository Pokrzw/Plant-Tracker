package com.example.planttrackerapp.helpingFunctions

import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver {

    fun onReceive(context: Context, intent: Intent) {

        //Logika countera dla każdej rośliny odliczającej czas podlania
        //if counter <= 0 to Toast else counter -1
        //Zbieraj też wszystkie rośliny 0>=, by powiedzieć, co potrzebuje uwagi i wyświetlić
        //Ale najpierw sprawdź czy notyfication działa overall


        Toast
            .makeText(context, "Tutaj wpisz powiadomienie", Toast.LENGTH_SHORT)
            .show()
    }
}