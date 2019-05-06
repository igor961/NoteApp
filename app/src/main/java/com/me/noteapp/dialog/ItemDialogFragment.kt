package com.me.noteapp.dialog


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.me.noteapp.NotesApplication
import com.me.noteapp.R
import com.me.noteapp.activity.MainActivity
import com.me.noteapp.entity.Item
import java.util.*


class ItemDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setCustomTitle(it.findViewById(R.id.customTitle)).setView(inflater.inflate(R.layout.fragment_item_dialog, null))
                    // Add action buttons
                    .setPositiveButton("Add",
                            DialogInterface.OnClickListener { dialog, id ->
                                val contentStr = getDialog().findViewById<EditText>(R.id.contentInput).text.toString()
                                NotesApplication.getDataManager().insertItem(Item(Date(), contentStr))
                                MainActivity.notifyDataSetChanged()
                            })
                    .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog().cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null") as Throwable
    }


}
