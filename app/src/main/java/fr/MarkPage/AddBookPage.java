package fr.MarkPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookPage extends AppCompatActivity {

    private EditText titleEditText;
    private EditText authorEditText;
    private EditText currentPageEditText;
    private EditText totalPageEditText;
    private Button saveButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_page);

        databaseHelper = new DatabaseHelper(this);

        titleEditText = findViewById(R.id.editTextTitle);
        authorEditText = findViewById(R.id.editTextAuthor);
        currentPageEditText = findViewById(R.id.editTextCurrentPage);
        totalPageEditText = findViewById(R.id.editTextTotalPages);
        saveButton = findViewById(R.id.buttonAddBook);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private void saveBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String currentPageString = currentPageEditText.getText().toString().trim();
        String totalPageString = totalPageEditText.getText().toString().trim();

        // Vérification des champs
        if (title.isEmpty() || author.isEmpty() || currentPageString.isEmpty() || totalPageString.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int currentPage = Integer.parseInt(currentPageString);
        int totalPage = Integer.parseInt(totalPageString);

        Book newBook = new Book(title, author, currentPage, totalPage);
        long result = databaseHelper.addBook(newBook);

        if (result != -1) {
            Toast.makeText(this, "Book saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving the book", Toast.LENGTH_SHORT).show();
        }
    }
}
