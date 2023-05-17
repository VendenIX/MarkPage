package fr.MarkPage;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Book implements Serializable {
    private String title;
    private int id;
    private String author;
    private int currentPage;
    private int totalPages;
    private Map<String, String> vocabulary;

    public Book(int id, String title, String author, int currentPage, int totalPages) {
        this.title = title;
        this.id = id;
        this.author = author;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.vocabulary = new HashMap<>();
    }

    public Book(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.currentPage = 1;
        this.totalPages = totalPages;
        this.vocabulary = new HashMap<>();
    }

    public Book(String title, String author, int totalPages, int currentPage) {
        this.title = title;
        this.author = author;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.vocabulary = new HashMap<>();
    }

    public Book() {
        this.title = "";
        this.author = "";
        this.currentPage = 1;
        this.totalPages = 1;
        this.vocabulary = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        return this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", author=" + author + ", currentPage=" + currentPage + ", totalPages=" + totalPages + '}';
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<String, String> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Map<String, String> vocabulary) {

        if(vocabulary != null) {
            this.vocabulary = vocabulary;
        }
    }

    public String printVocabulary() {
        String result = "";
        for (Map.Entry<String, String> entry : vocabulary.entrySet()) {
            result += entry.getKey() + " : " + entry.getValue() + "\n";
        }

        if(result == "") {
            result = "No vocabulary yet";
        }
        return result;
    }

    public String getVocabularyJson() {
        // Utiliser une bibliothèque de sérialisation JSON comme Gson pour convertir la Map en JSON
        Gson gson = new Gson();
        return gson.toJson(vocabulary);
    }

    public void addVocabulary(String word, String definition) {
        if( vocabulary == null) {
            vocabulary = new HashMap<>();
        }
        vocabulary.put(word, definition);
    }

}
