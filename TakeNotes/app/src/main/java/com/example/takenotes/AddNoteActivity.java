package com.example.takenotes;

import com.example.takenotes.model.Note;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    Button saveNoteBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveNoteBtn = findViewById(R.id.saveNoteBtn);
        db = FirebaseFirestore.getInstance();

        saveNoteBtn.setOnClickListener(view -> {
            String title = titleEditText.getText().toString().trim();
            String content = contentEditText.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Note note = new Note(title, content);
            db.collection("notes").add(note)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show());
        });
    }
}
