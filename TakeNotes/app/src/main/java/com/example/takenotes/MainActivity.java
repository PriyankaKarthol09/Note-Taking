package com.example.takenotes;

import com.example.takenotes.model.Note;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addNoteFab;
    FirebaseFirestore db;
    NotesAdapter notesAdapter;
    ArrayList<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notesRecyclerView);
        addNoteFab = findViewById(R.id.addNoteFab);
        db = FirebaseFirestore.getInstance();

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(this, noteList);

        // 2 columns (i.e., 2 notes side-by-side, stacked vertically)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(notesAdapter);

        fetchNotesFromFirebase();

        addNoteFab.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });
    }

    private void fetchNotesFromFirebase() {
        db.collection("notes")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error fetching notes", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    noteList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        Note note = doc.toObject(Note.class);
                        note.setId(doc.getId());
                        noteList.add(note);
                    }
                    notesAdapter.notifyDataSetChanged();
                });
    }
}
