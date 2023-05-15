package fr.MarkPage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BookPage extends AppCompatActivity {
    private EditText titleTextView;
    private EditText authorTextView;
    private SeekBar bookProgressSeekBar;
    private TextView progressText;
    DatabaseHelper dbHelper;
    Button updateButton;
    Button deleteButton;
    Book currentBook;


    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce livre ?");
        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Supprimer le livre
                dbHelper.deleteBook(currentBook);
                Toast.makeText(BookPage.this, "Book deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Annuler la suppression
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_item_update);
        currentBook = (Book) getIntent().getSerializableExtra("selectedBook");

        /*
        EditText titleEditText = findViewById(R.id.book_title);
        titleEditText.setText(currentBook.getTitle());

        EditText authorTextView = findViewById(R.id.book_author);
        authorTextView.setText(currentBook.getAuthor());
        */

        // Utilisez cette référence pour accéder aux vues qu'il contient
        titleTextView = findViewById(R.id.book_title);
        authorTextView = findViewById(R.id.book_author);
        bookProgressSeekBar = findViewById(R.id.book_progress);
        progressText = findViewById(R.id.progress_text);
        updateButton = findViewById(R.id.buttonUpdateBook);
        deleteButton = findViewById(R.id.buttonDeleteBook);
        dbHelper = new DatabaseHelper(this);

        // Obtenez le livre actuel en utilisant l'intention qui a lancé cette activité
        currentBook = (Book) getIntent().getSerializableExtra("selectedBook");

        // Mettez à jour les vues avec les informations du livre
        titleTextView.setText(currentBook.getTitle());
        authorTextView.setText(currentBook.getAuthor());
        bookProgressSeekBar.setMax(currentBook.getTotalPages());
        bookProgressSeekBar.setProgress(currentBook.getCurrentPage());
        int pourcentages = (int) ((currentBook.getCurrentPage() * 100) / currentBook.getTotalPages());
        progressText.setText("Page " + currentBook.getCurrentPage() + " sur " + currentBook.getTotalPages() + " (" +pourcentages + "%)");

        // Ajoutez un listener à la SeekBar pour mettre à jour le texte de progression
        bookProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pourcentagesp = (int) ((progress * 100) / currentBook.getTotalPages());
                progressText.setText("Page " + progress+ " sur " + currentBook.getTotalPages() + " (" +pourcentagesp + "%)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Ajoutez un listener au bouton pour mettre à jour le nombre de pages lues
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBook.setCurrentPage(bookProgressSeekBar.getProgress());
                dbHelper.updateBook(currentBook);
                Toast.makeText(BookPage.this, "Book updated", Toast.LENGTH_SHORT).show();
                // Vérifiez si le titre a été modifié

                EditText titleEditText = findViewById(R.id.book_title);
                String updatedTitle = titleEditText.getText().toString().trim();
                if (!updatedTitle.equals(currentBook.getTitle())) {
                    currentBook.setTitle(updatedTitle);
                    dbHelper.updateBook(currentBook);
                }

                EditText authorEditText = findViewById(R.id.book_author);
                String updatedAuthor = authorEditText.getText().toString().trim();
                if (!updatedAuthor.equals(currentBook.getAuthor())) {
                    currentBook.setAuthor(updatedAuthor);
                    dbHelper.updateBook(currentBook);
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

    }


}
