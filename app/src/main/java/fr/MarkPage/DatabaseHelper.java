package fr.MarkPage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.MarkPage.Book;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_database";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_CURRENT_PAGE = "current_page";
    private static final String COLUMN_TOTAL_PAGES = "total_pages";
    private static final String COLUMN_VOCABULARY = "vocabulary";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_AUTHOR + " TEXT," +
                COLUMN_CURRENT_PAGE + " INTEGER," +
                COLUMN_TOTAL_PAGES + " INTEGER," +
                COLUMN_VOCABULARY + " TEXT" +
                ")";
        db.execSQL(CREATE_BOOKS_TABLE);
        List<Book> bookList = getAllBooks();
        /*
        if (!bookList.isEmpty()) {
            Book firstBook = bookList.get(0);

            // Ajouter des mots et des définitions à votre livre
            firstBook.addVocabulary("ABRUTILAND1", "ABRUTILAND1");
            firstBook.addVocabulary("ABRUTILAND2", "ABRUTILAND2");
            firstBook.addVocabulary("ABRUTILAND3", "ABRUTILAND3");
            firstBook.addVocabulary("ABRUTILAND4", "ABRUTILAND4");

            // Convertir le vocabulaire en JSON
            String vocabularyJson = firstBook.getVocabularyJson();

            // Mettre à jour la base de données avec le vocabulaire
            updateVocabulary(firstBook, vocabularyJson);
        }
        */


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create tables again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public long addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_CURRENT_PAGE, book.getCurrentPage());
        values.put(COLUMN_TOTAL_PAGES, book.getTotalPages());
        values.put(COLUMN_VOCABULARY, ""); // Initial value for vocabulary

        long id = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                book.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                book.setCurrentPage(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_PAGE)));
                book.setTotalPages(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_PAGES)));
                // Récupérer le vocabulaire du livre
                String vocabularyJson = cursor.getString(cursor.getColumnIndex(COLUMN_VOCABULARY));
                if (vocabularyJson != null && !vocabularyJson.isEmpty()) {
                    Map<String, String> vocabulary = new HashMap<>();

                    try {
                        JSONObject json = new JSONObject(vocabularyJson);
                        Iterator<String> keys = json.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            String value = json.getString(key);
                            vocabulary.put(key, value);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    book.setVocabulary(vocabulary);
                } else {
                    // Définir un vocabulaire par défaut si nécessaire
                    book.setVocabulary(new HashMap<>());
                }

                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookList;
    }



    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_CURRENT_PAGE, book.getCurrentPage());
        values.put(COLUMN_TOTAL_PAGES, book.getTotalPages());

        db.update(TABLE_BOOKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public void deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public void updateVocabulary(Book book, String vocabularyJson) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VOCABULARY, vocabularyJson);

        Log.i("DatabaseHelper", "Vocabulary to update: " + vocabularyJson);
        // Log pour vérifier si la mise à jour a réussi.
        int rowsAffected = db.update(TABLE_BOOKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        db.close();
        Log.i("DatabaseHelper", "updateVocabulary: Rows affected = " + rowsAffected);
    }

    @SuppressLint("Range")
    public String getVocabulary(Book book) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_VOCABULARY},
                COLUMN_ID + " = ?", new String[]{String.valueOf(book.getId())},
                null, null, null);

        String vocabularyJson = "";

        if (cursor.moveToFirst()) {
            vocabularyJson = cursor.getString(cursor.getColumnIndex(COLUMN_VOCABULARY));
        }

        cursor.close();
        db.close();

        Log.i("DatabaseHelper", "Retrieved Vocabulary: " + vocabularyJson);

        return vocabularyJson;
    }

    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Book book = null;

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_CURRENT_PAGE, COLUMN_TOTAL_PAGES, COLUMN_VOCABULARY}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
            @SuppressLint("Range") int currentPage = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_PAGE));
            @SuppressLint("Range") int totalPages = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_PAGES));

            book = new Book(id, title, author, currentPage, totalPages);

            // Récupérer le vocabulaire en format JSON
            @SuppressLint("Range") String vocabularyJson = cursor.getString(cursor.getColumnIndex(COLUMN_VOCABULARY));

            // Convertir le JSON en Map
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            Map<String, String> vocabulary = gson.fromJson(vocabularyJson, type);

            // Définir le vocabulaire du livre
            book.setVocabulary(vocabulary);
        }

        if (cursor != null) {
            cursor.close();
        }

        return book;
    }
}
