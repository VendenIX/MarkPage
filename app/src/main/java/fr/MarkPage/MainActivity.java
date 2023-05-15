package fr.MarkPage;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.MarkPage.Book;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList;
    private ArrayAdapter<Book> bookAdapter;
    private ListView listViewBooks;
    private Button buttonAddBook;
    private static final int ADD_BOOK_REQUEST_CODE = 1;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        listViewBooks = findViewById(R.id.listViewBooks);
        buttonAddBook = findViewById(R.id.buttonAddBook);

        // Lire les livres de la base de données au lieu du fichier JSON
        bookList = databaseHelper.getAllBooks();

        bookAdapter = new BookListAdapter(this,R.layout.book_item, bookList);
        listViewBooks.setAdapter(bookAdapter);

        if (bookList != null && bookList.size() > 0) {
            bookAdapter.notifyDataSetChanged();
        } else {
            // Afficher un message si la liste est vide
            Toast.makeText(this, "No books found", Toast.LENGTH_SHORT).show();
        }

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book selectedBook = bookList.get(i);
                Intent intent = new Intent(MainActivity.this, UpdateBookPage.class);

                // Add the selected book as an extra to the intent
                intent.putExtra("selectedBook", selectedBook);

                // Start the activity
                startActivity(intent);
            }
        });

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookPage.class);
                startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            Book newBook = (Book) data.getSerializableExtra("newBook");
            // Ajouter le nouveau livre à la base de données
            databaseHelper.addBook(newBook);
            // Mettre à jour la liste des livres et rafraîchir l'adapter
            bookList = databaseHelper.getAllBooks();
            bookAdapter.clear();
            bookAdapter.addAll(bookList);
            bookAdapter.notifyDataSetChanged();
            Toast.makeText(this, "New book added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pas besoin de sauvegarder les livres dans un fichier,
        // car ils sont déjà stockés dans la base de données SQLite
    }

    private void loadBooksFromDatabase() {
        bookList.clear();
        bookList.addAll(databaseHelper.getAllBooks());
        bookAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadBooksFromDatabase();
    }

}

