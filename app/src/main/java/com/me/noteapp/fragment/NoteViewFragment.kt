package com.me.noteapp.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.me.noteapp.R
import com.me.noteapp.entity.Item

class NoteViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val i = arguments?.getSerializable("note") as Item
        val inflatedView = inflater.inflate(R.layout.fragment_note_view, container, false)
        inflatedView.findViewById<TextView>(R.id.noteView)!!.text = i.content
        return inflatedView
    }

    companion object {
        @JvmStatic
        fun newInstance(note: Item): NoteViewFragment {
            val f = NoteViewFragment()
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            f.arguments = bundle
            return f
        }
    }

}
