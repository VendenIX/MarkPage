package fr.MarkPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BookItemUpdateFragment extends Fragment {

    private EditText titleTextView;
    private EditText authorTextView;
    private SeekBar bookProgressSeekBar;
    private TextView progressText;
    private DatabaseHelper dbHelper;
    private Button updateButton;
    private Button deleteButton;
    private Book currentBook;

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce livre ?");
        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Supprimer le livre
                dbHelper.deleteBook(currentBook);
                Toast.makeText(requireContext(), "Book deleted", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_item_update, container, false);

        // Utilisez cette référence pour accéder aux vues qu'il contient
        titleTextView = rootView.findViewById(R.id.book_title);
        authorTextView = rootView.findViewById(R.id.book_author);
        bookProgressSeekBar = rootView.findViewById(R.id.book_progress);
        progressText = rootView.findViewById(R.id.progress_text);
        updateButton = rootView.findViewById(R.id.buttonUpdateBook);
        deleteButton = rootView.findViewById(R.id.buttonDeleteBook);
        dbHelper = new DatabaseHelper(requireContext());

        // Obtenez le livre actuel en utilisant l'intention qui a lancé cette activité
        currentBook = (Book) requireActivity().getIntent().getSerializableExtra("selectedBook");

        // Mettez à jour les vues avec les informations du livre
        titleTextView.setText(currentBook.getTitle());
        authorTextView.setText(currentBook.getAuthor());
        bookProgressSeekBar.setMax(currentBook.getTotalPages());
        bookProgressSeekBar.setProgress(currentBook.getCurrentPage());
        int pourcentage = (int) ((currentBook.getCurrentPage() * 100) / currentBook.getTotalPages());
        progressText.setText("Page " + currentBook.getCurrentPage() + " sur " + currentBook.getTotalPages() + " (" + pourcentage + "%)");

        // Ajoutez un listener à la SeekBar pour mettre à jour le texte de progression
        bookProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pourcentage = (int) ((progress * 100) / currentBook.getTotalPages());
                progressText.setText("Page " + progress + " sur " + currentBook.getTotalPages() + " (" + pourcentage + "%)");
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
                Toast.makeText(requireContext(), "Book updated", Toast.LENGTH_SHORT).show();
                // Vérifiez si le titre a été modifié

                String updatedTitle = titleTextView.getText().toString().trim();
                if (!updatedTitle.equals(currentBook.getTitle())) {
                    currentBook.setTitle(updatedTitle);
                    dbHelper.updateBook(currentBook);
                }

                String updatedAuthor = authorTextView.getText().toString().trim();
                if (!updatedAuthor.equals(currentBook.getAuthor())) {
                    currentBook.setAuthor(updatedAuthor);
                    dbHelper.updateBook(currentBook);
                }
                requireActivity().finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        return rootView;
    }
}


