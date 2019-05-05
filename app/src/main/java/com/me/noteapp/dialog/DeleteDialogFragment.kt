package com.me.noteapp.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.me.noteapp.NotesApplication
import com.me.noteapp.R
import com.me.noteapp.activity.MainActivity
import com.me.noteapp.activity.NoteActivity
import com.me.noteapp.entity.Item
import java.util.*

class DeleteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setCustomTitle(it.findViewById(R.id.customTitle))
                    .setView(inflater.inflate(R.layout.fragment_delete_dialog, null))
                    .setPositiveButton(R.string.title_delete) { dialog, which ->
                        NotesApplication.getDataManager().deleteItem(arguments?.getLong("id"))
                        it.finish()
                    }.setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                        it.finish()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long): DeleteDialogFragment {
            val b = Bundle()
            b.putLong("id", id)
            val ddf = DeleteDialogFragment()
            ddf.arguments = b
            return ddf
        }
    }

}