package com.example.notesapp.domain;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FireStoreNotesRepository implements NotesRepository{

    public static final String KEY_TITLE = "title";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String NOTES = "notes";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();



    @Override
    public void getAll(Callback<List<Note>> callback) {
        firestore.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                            String id = documentSnapshot.getId();

                            String title = documentSnapshot.getString(KEY_TITLE);
                            String details = documentSnapshot.getString(KEY_DETAILS);
                            Date createdAt = documentSnapshot.getDate(KEY_CREATED_AT);

                            result.add(new Note(id, title, details, createdAt));
                        }

                        callback.onSuccess(result);

                    }
                });
    }

    @Override
    public void addNote(String title, String details, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        Date createdAt = new Date();

        data.put(KEY_TITLE, title);
        data.put(KEY_DETAILS, details);
        data.put(KEY_CREATED_AT, createdAt);


        firestore.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        callback.onSuccess(new Note(documentReference.getId(), title, details, createdAt));
                    }
                });
    }

    @Override
    public void deleteNote(Note note, Callback<Void> callback) {

        firestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                });


    }

    @Override
    public void updateNote(Note note, String title, String details, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        Date updatedAt = new Date();

        data.put(KEY_TITLE, title);
        data.put(KEY_DETAILS, details);
        data.put(KEY_CREATED_AT, updatedAt);

        firestore.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Note result = new Note(note.getId(), title, details, updatedAt);

                        callback.onSuccess(result);
                    }
                });

    }
}
