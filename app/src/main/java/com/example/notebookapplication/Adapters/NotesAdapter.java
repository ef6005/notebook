package com.example.notebookapplication.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.notebookapplication.database.Note;
import com.example.notebookapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> mNotes = new ArrayList<>();
    private OnNoteEventListener mOnNonteEventListener;

    public NotesAdapter(@NonNull OnNoteEventListener onNonteEventListener){
        this.mOnNonteEventListener = onNonteEventListener;
    }

    public void addNote(Note note){
        mNotes.add(note);
        notifyItemInserted(mNotes.size()-1);
    }

    public void updateNote(Note note){
        for (int i = 0; i <mNotes.size() ; i++) {
            if( mNotes.get(i).getId() == note.getId()){
                mNotes.set(i,note);
                notifyItemChanged(i);
                //TODO:4
                //id is unique so there is maximum one Note with given noteId
//                break;
            }
        }
    }

    public void updateNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    public void deleteNoteById(int noteId){
        for (int i = 0; i <mNotes.size() ; i++) {
            if( mNotes.get(i).getId() == noteId){
                mNotes.remove(i);
                notifyItemRemoved(i);
                //TODO:4
                //id is unique so there is maximum one Note with given noteId
//                break;
            }
        }
    }

    public void deleteAllNotes(){
        mNotes.clear();
       // mNotes = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NoteViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.bindNote(mNotes.get(i));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv;
        private TextView descriptionTv;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.itemNote_tv_title);
            descriptionTv = itemView.findViewById(R.id.itemNote_tv_description);
            Button editBtn = itemView.findViewById(R.id.itemNote_btn_edit);
            editBtn.setOnClickListener(view -> mOnNonteEventListener.onEditNoteClicked(mNotes.get(getAdapterPosition())));
            Button deleteButton = itemView.findViewById(R.id.itemNote_btn_delete);
            deleteButton.setOnClickListener(view-> mOnNonteEventListener.onDeleteNoteClicked(mNotes.get(getAdapterPosition())));
        }

        public void bindNote(Note note){
            titleTv.setText(note.getTitle());
            descriptionTv.setText(note.getDescription());
        }
    }

    public interface OnNoteEventListener{
        void onEditNoteClicked(Note note);
        void onDeleteNoteClicked(Note note);
    }
}
