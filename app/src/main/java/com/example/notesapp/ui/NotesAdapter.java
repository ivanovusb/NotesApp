package com.example.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy / HH:mm", Locale.getDefault());

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked noteClicked;

    private List<Note> data = new ArrayList<>();

    public void setData(Collection<Note> notes) {
        data.addAll(notes);
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }


    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        NotesViewHolder holder = new NotesViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Note note = data.get(position);
        holder.title.setText(note.getTitle());
        holder.details.setText(note.getDetails());
        holder.date.setText(dateFormat.format(note.getCreatedDate()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView details;
        TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            date = itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (noteClicked != null) {
                        noteClicked.onNoteClicked(data.get(clickedPosition));

                    }
                }
            });
        }


    }
}
