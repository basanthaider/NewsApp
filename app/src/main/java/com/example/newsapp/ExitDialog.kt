package com.example.newsapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ExitDialog:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        val builder = AlertDialog.Builder(activity)
            .setIcon(android.R.drawable.ic_menu_close_clear_cancel )
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("yes") { _, _ -> activity?.finish() }
            .setNegativeButton("No"){ dialog, _ ->dialog.dismiss()}
        return builder.create()
    }
}