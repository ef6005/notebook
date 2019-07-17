package com.example.notebookapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.notebookapplication.Adapters.EditNoteDialogFragment;
import com.example.notebookapplication.Adapters.NotesAdapter;
import com.example.notebookapplication.database.Note;
import com.example.notebookapplication.database.NoteDao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditNoteDialogFragment.OnNoteChangedListener, NotesAdapter.OnNoteEventListener {

    private NotesAdapter mNotesAdapter;
    private NoteDao mNoteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup dao and adapter
        mNoteDao = AppDatabase.getInstance(this).getNoteDao();
        mNotesAdapter = new NotesAdapter(this);
        //TODO:2 move to background thread
        mNotesAdapter.updateNotes(mNoteDao.getAll());

        //recycler view
        RecyclerView notesRv = findViewById(R.id.main_rv_notes);
        notesRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        notesRv.setAdapter(mNotesAdapter);

        //add button
        Button addNewNoteBtn = findViewById(R.id.main_btn_addNewNote);
        addNewNoteBtn.setOnClickListener(this);

        //delete button
        Button deleteAllBtn = findViewById(R.id.main_btn_deleteAll);
        deleteAllBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_addNewNote:
                EditNoteDialogFragment editNoteDialogFragment = EditNoteDialogFragment.newInstance(this,null);
                editNoteDialogFragment.show(getSupportFragmentManager(),null);
                break;

            case R.id.main_btn_deleteAll:
                //TODO:3 prompt for delete all notes in a dialog then delete
                //TODO:2 move to background thread
                mNoteDao.deleteAll();
                //delete adapter list
                mNotesAdapter.deleteAllNotes();
                break;
        }
    }

    @Override
    public void onSaveClicked(Note note) {
        //if note is a new note add it
        if( ! note.hasDatabaseId ) {
            //TODO:2 move to background thread
            note.hasDatabaseId =true;
            mNoteDao.insert(note);

            //then add to adapter
            mNotesAdapter.addNote(note);
        }
        else{//if note is an edited one , update it
            //TODO:2 move to background thread
            mNoteDao.update(note);
            //update note in adapter
            mNotesAdapter.updateNote(note);
        }
    }

    @Override
    public void onEditNoteClicked(Note note) {
        //show in a dialog and stay for result on listener implementation:onSaveClicked()
        EditNoteDialogFragment editNoteDialogFragment = EditNoteDialogFragment.newInstance(this,note);
        editNoteDialogFragment.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onDeleteNoteClicked(Note note) {
        //TODO:2 move to background thread
        mNoteDao.delete(note);
        //delete note from adapter
        mNotesAdapter.deleteNoteById(note.getId());
    }
}
