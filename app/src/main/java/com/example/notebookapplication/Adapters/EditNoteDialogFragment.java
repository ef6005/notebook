package com.example.notebookapplication.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notebookapplication.database.Note;
import com.example.notebookapplication.R;

public class EditNoteDialogFragment extends DialogFragment implements View.OnClickListener {

    private static OnNoteChangedListener mOnNoteChangedListener;
    EditText titleEt;
    EditText descriptionEt;
    Button saveBtn;
    Button cancelBtn;


    public static EditNoteDialogFragment newInstance(@NonNull OnNoteChangedListener onNoteChangedListener,@Nullable Note note ) {
        mOnNoteChangedListener = onNoteChangedListener;

        EditNoteDialogFragment fragment = new EditNoteDialogFragment();

        if (note != null) {
            Bundle args = new Bundle();
            args.putParcelable("note", note);
            fragment.setArguments(args);
        }
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.edit_note_page,null,false);
        builder.setView(rootView);

        titleEt = rootView.findViewById(R.id.editNote_et__title);
        descriptionEt = rootView.findViewById(R.id.editNote_et_description);
        saveBtn = rootView.findViewById(R.id.editNote_btn_save);
        saveBtn.setOnClickListener(this);
        cancelBtn = rootView.findViewById(R.id.editNote_btn_cancel);
        cancelBtn.setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {
            Note note = args.getParcelable("note");
            titleEt.setText(note.getTitle());
            descriptionEt.setText(note.getDescription());
        }

        return builder.create();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editNote_btn_save:
                Note note;

                Bundle args = getArguments();
                if (args == null) {
                    note = new Note();
                }
                else{
                    note = args.getParcelable("note");
                }

                note.setTitle(titleEt.getText().toString());
                note.setDescription(descriptionEt.getText().toString());

                mOnNoteChangedListener.onSaveClicked(note);
                dismiss();
                break;
            case R.id.editNote_btn_cancel:
                dismiss();
                break;
        }
    }

    public interface OnNoteChangedListener{
        void onSaveClicked(Note note);
        //void onCancelClicked();
    }
}
