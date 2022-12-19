package com.example.notesapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.example.notesapp.domain.NoteCreateRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    private FloatingActionButton fabAdd;
    List<Note> notes;
    NoteCreationFragment noteCreationFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        NotesAdapter adapter = new NotesAdapter();
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SELECTED_NOTE, note);
                    getParentFragmentManager()
                            .setFragmentResult(NOTES_CLICKED_KEY, bundle);
                } else {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                            .addToBackStack("details")
                            .commit();
                }
            }
        });


        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        Toolbar toolbar = view.findViewById(R.id.toolbar_notes_list);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(requireContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_sort:
                        Toast.makeText(requireContext(), "SORT", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, noteCreationFragment = new NoteCreationFragment())
                        .addToBackStack("")
                        .commit();
            }
        });


        notesList.setAdapter(adapter);
        notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        adapter.setData(notes);

        adapter.notifyDataSetChanged();


        MainActivity.onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };

    }


}
