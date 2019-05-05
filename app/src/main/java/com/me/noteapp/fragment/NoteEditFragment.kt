package com.me.noteapp.fragment


import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.me.noteapp.NotesApplication

import com.me.noteapp.R
import com.me.noteapp.entity.Item
import kotlinx.android.synthetic.main.activity_note.view.*
import java.util.*
import kotlin.coroutines.coroutineContext


class NoteEditFragment : Fragment() {
    private lateinit var editNoteView: EditText
    private lateinit var mItem: Item


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        mItem = arguments?.getSerializable("note") as Item
        val inflatedView = inflater.inflate(R.layout.fragment_note_edit, container, false)
        editNoteView = inflatedView.findViewById<EditText>(R.id.editNoteView)
        editNoteView.setText(mItem.content)
        return inflatedView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId?.equals(R.id.action_save)!!) {
            val newContent = editNoteView.text.toString()
            doAsync {
                NotesApplication.getDataManager().updateItem(Item(mItem.getdAt(), newContent))
                activity?.finish()
            }.execute()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Item): NoteEditFragment {
            val f = NoteEditFragment()
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            f.arguments = bundle
            return f
        }
    }

}
