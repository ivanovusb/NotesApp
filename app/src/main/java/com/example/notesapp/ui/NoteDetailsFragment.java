package com.example.notesapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notesapp.R;
import com.example.notesapp.domain.Callback;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailsFragment extends Fragment {

    private EditText title;
    private EditText details;
    private FloatingActionButton fabEdit;
    private Note noteToEdit;

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String EDIT_KEY_RESULT = "NoteDetailsFragment_EDIT_KEY_RESULT";

    public static NoteDetailsFragment editInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteToEdit = null;

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            noteToEdit = getArguments().getParcelable(ARG_NOTE);
        }

        MainActivity.onBackPressedCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {

            }
        };

        title = view.findViewById(R.id.note_edit_label);
        details = view.findViewById(R.id.note_edit_text);
        fabEdit = view.findViewById(R.id.fab_edit);

        if (noteToEdit != null) {
            title.setText(noteToEdit.getTitle());
            details.setText(noteToEdit.getDetails());
        }


        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabEdit.setEnabled(false);
                InMemoryNotesRepository.getInstance(requireContext()).updateNote(noteToEdit, title.getText().toString(), details.getText().toString(), new Callback<Note>() {
                    @Override
                    public void onSuccess(Note data) {

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager()
                                .setFragmentResult(EDIT_KEY_RESULT, bundle);

                        fabEdit.setEnabled(true);
                        getParentFragmentManager()
                                .popBackStack();
                    }

                    @Override
                    public void onError(Throwable exception) {
                        fabEdit.setEnabled(true);

                    }
                });
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_note_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:

                        new AlertDialog.Builder(requireContext())
                                .setTitle(getResources().getString(R.string.delete_dialog_title))
                                .setMessage(getResources().getString(R.string.delete_dialog_message))
                                .setIcon(R.drawable.ic_error_24)
                                .setCancelable(false)
                                .setPositiveButton(R.string.positive_answer, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton(R.string.negative_answer, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_info:
                        Toast.makeText(requireContext(), "info", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_share:
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        Intent intent = Intent.createChooser(sendIntent, "Выбор приложения");
                        startActivity(intent);
                        Toast.makeText(requireContext(), "share", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_attach:
                        Toast.makeText(requireContext(), "attach", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

    }


}
