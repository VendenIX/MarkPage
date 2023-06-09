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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_page);

        // Charger le fragment BookItemUpdateFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new BookItemUpdateFragment());
        fragmentTransaction.commit();

        // Charger le fragment VocabularyListFragment
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.replace(R.id.vocabularyFragmentContainer, new VocabularyListFragment());
        fragmentTransaction2.commit();

        // Charger le fragment AddVocabularyFragment
        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
        fragmentTransaction3.replace(R.id.addVocabularyFragmentContainer, new AddVocabularyFragment());
        fragmentTransaction3.commit();

        Book currentBook = (Book) getIntent().getSerializableExtra("selectedBook");
        Toast.makeText(this, "vocabulary: " + currentBook.printVocabulary(), Toast.LENGTH_SHORT).show();

    }
}