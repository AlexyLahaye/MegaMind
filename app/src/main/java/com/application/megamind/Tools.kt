package com.application.megamind

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

class Tools {
    companion object{
        fun displayError(context: Context, string: String){
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Error")
            alert.setMessage(string)
            val alertDialog = alert.create()
            alertDialog.show()
        }
    }
}