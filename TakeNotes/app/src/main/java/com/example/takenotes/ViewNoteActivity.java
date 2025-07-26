package com.example.takenotes;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    TextView titleTextView, contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        titleTextView = findViewById(R.id.viewNoteTitle);
        contentTextView = findViewById(R.id.viewNoteContent);

        // Get data from Intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}
