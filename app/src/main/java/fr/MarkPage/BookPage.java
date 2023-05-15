package fr.MarkPage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookPage extends AppCompatActivity {
    private TextView titleTextView;
    private TextView authorTextView;
    private ProgressBar bookProgressBar;
    private TextView progressText;
    DatabaseHelper dbHelper;
    Button deleteButton;
    Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_page);
        View includedLayout = findViewById(R.id.includedLayout);
        deleteButton = findViewById(R.id.buttonDeleteBook);
        currentBook = (Book) getIntent().getSerializableExtra("selectedBook");
        // Utilisez cette référence pour accéder aux vues qu'il contient
        titleTextView = includedLayout.findViewById(R.id.book_title);
        authorTextView = includedLayout.findViewById(R.id.book_author);
        bookProgressBar = includedLayout.findViewById(R.id.book_progress);
        progressText = includedLayout.findViewById(R.id.progress_text);

        titleTextView.setText(currentBook.getTitle());
        authorTextView.setText(currentBook.getAuthor());
        bookProgressBar.setMax(currentBook.getTotalPages());
        bookProgressBar.setProgress(currentBook.getCurrentPage());
        int pourcentages = (int) ((currentBook.getCurrentPage() * 100) / currentBook.getTotalPages());
        progressText.setText("Page " + currentBook.getCurrentPage() + " sur " + currentBook.getTotalPages() + " (" +pourcentages + "%)");


        dbHelper = new DatabaseHelper(this);

        // Obtenez le livre actuel en utilisant l'intention qui a lancé cette activité

        Toast.makeText(BookPage.this, currentBook.getTitle(), Toast.LENGTH_SHORT).show();


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteBook(currentBook);
                Toast.makeText(BookPage.this, "Book deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
