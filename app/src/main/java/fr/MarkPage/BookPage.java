package fr.MarkPage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BookPage extends AppCompatActivity {
    private BookItemUpdateFragment bookItemUpdateFragment;
    private Book currentBook; // Ajoutez une variable pour le livre en cours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_page);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Charger le fragment BookItemUpdateFragment
        bookItemUpdateFragment = (BookItemUpdateFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (bookItemUpdateFragment == null) {
            bookItemUpdateFragment = new BookItemUpdateFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, bookItemUpdateFragment);
            fragmentTransaction.commit();
        }

        // Charger le fragment VocabularyListFragment
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.replace(R.id.vocabularyFragmentContainer, VocabularyListFragment.newInstance());
        fragmentTransaction2.commit();

        // Charger le fragment AddVocabularyFragment
        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
        fragmentTransaction3.replace(R.id.addVocabularyFragmentContainer, new AddVocabularyFragment());
        fragmentTransaction3.commit();

        // Récupérez l'ID du livre de l'intention
        int selectedBookId = getIntent().getIntExtra("selectedBookId", -1);
        if (selectedBookId == -1) {
            Log.e("BookPage", "No book ID found in intent");
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Remplacez "this" par le contexte approprié
        // Récupérez le livre de la base de données
        currentBook = databaseHelper.getBook(selectedBookId);
        if (currentBook == null) {
            Log.e("BookPage", "No book found in database with ID: " + selectedBookId);
            return;
        }

        // Ajoutez du vocabulaire au livre
        currentBook.addVocabulary("mot1", "définition1");
        currentBook.addVocabulary("mot2", "définition2");
        currentBook.addVocabulary("mot3", "définition3");
        currentBook.addVocabulary("mot4", "définition4");
        currentBook.addVocabulary("mot5", "définition5");
        currentBook.addVocabulary("mot6", "définition6");
        currentBook.addVocabulary("mot7", "définition7");
        currentBook.addVocabulary("mot8", "définition8");
        currentBook.addVocabulary("mot9", "définition9");
        currentBook.addVocabulary("mot10", "définition10");
        currentBook.addVocabulary("mot11", "définition11");
        currentBook.addVocabulary("mot12", "définition12");

        String vocabularyJson = currentBook.getVocabularyJson();
        databaseHelper.updateVocabulary(currentBook, vocabularyJson);

        // Appel de la méthode getVocabulary
        String retrievedVocabulary = databaseHelper.getVocabulary(currentBook);
        Log.i("BookPage", "Retrieved vocabulary from database: " + retrievedVocabulary);
        Toast.makeText(this, "vocabulary: " + currentBook.printVocabulary(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "vocaVOCA" + currentBook.getId(), Toast.LENGTH_SHORT).show();
    }

    public Book getCurrentBook() {
        if (bookItemUpdateFragment != null) {
            return bookItemUpdateFragment.getCurrentBook();
        }
        return null;
    }
}

