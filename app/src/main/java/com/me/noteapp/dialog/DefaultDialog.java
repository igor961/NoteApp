package com.me.noteapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.me.noteapp.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

abstract public class DefaultDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setCustomTitle(getActivity().findViewById(R.id.customTitle))
                .setView(inflater.inflate(getRes(), null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                action();
                            }
                        }

                ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (finish()) getActivity().finish();
            }
        });
        return builder.create();
    }

    protected abstract boolean finish();

    protected abstract int getRes();

    abstract void action();
}
