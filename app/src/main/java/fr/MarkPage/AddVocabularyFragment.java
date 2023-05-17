package fr.MarkPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.MarkPage.Book;
import fr.MarkPage.DatabaseHelper;

public class AddVocabularyFragment extends Fragment {

    private EditText wordEditText;
    private EditText definitionEditText;
    private Button addButton;
    private DatabaseHelper dbHelper;
    private Book currentBook;

    public AddVocabularyFragment() {
        // Required empty public constructor
    }

    public static AddVocabularyFragment newInstance() {
        return new AddVocabularyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
        currentBook = ((BookPage) requireActivity()).getCurrentBook();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vocabulary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordEditText = view.findViewById(R.id.editTextWord);
        definitionEditText = view.findViewById(R.id.editTextDefinition);
        addButton = view.findViewById(R.id.buttonAddVocabulary);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick: " + wordEditText.getText().toString().trim() + " " + definitionEditText.getText().toString().trim());
                Log.i("AddVocabularyFragment", "onClick" + currentBook.printVocabulary());
                String word = wordEditText.getText().toString().trim();
                String definition = definitionEditText.getText().toString().trim();

                if (!word.isEmpty() && !definition.isEmpty()) {
                    currentBook.addVocabulary(word, definition);
                    dbHelper.updateVocabulary(currentBook, currentBook.getVocabularyJson());

                    wordEditText.setText("");
                    definitionEditText.setText("");

                    // Afficher un message de succès
                    Toast.makeText(requireContext(), "Vocabulary added", Toast.LENGTH_SHORT).show();
                } else {
                    // Afficher un message d'erreur si les champs sont vides
                    Toast.makeText(requireContext(), "Please enter a word and definition", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
