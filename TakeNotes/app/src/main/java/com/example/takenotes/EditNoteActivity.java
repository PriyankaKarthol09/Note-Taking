package com.example.takenotes;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takenotes.model.Note;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNoteActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    Button updateButton, deleteButton;

    FirebaseFirestore db;
    String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.editTitle);
        contentEditText = findViewById(R.id.editContent);
        updateButton = findViewById(R.id.updateNoteButton);
        deleteButton = findViewById(R.id.deleteNoteButton);

        db = FirebaseFirestore.getInstance();

        // Receive data from intent
        noteId = getIntent().getStringExtra("noteId");
        String noteTitle = getIntent().getStringExtra("noteTitle");
        String noteContent = getIntent().getStringExtra("noteContent");

        titleEditText.setText(noteTitle);
        contentEditText.setText(noteContent);

        updateButton.setOnClickListener(v -> updateNote());
        deleteButton.setOnClickListener(v -> deleteNote());
    }

    private void updateNote() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Both fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("notes").document(noteId)
                .update("title", title, "content", content)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show());
    }

    private void deleteNote() {
        db.collection("notes").document(noteId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show());
    }
}
