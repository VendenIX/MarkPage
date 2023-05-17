package fr.MarkPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class VocabularyItemFragment extends Fragment {
    private static final String ARG_WORD = "word";
    private static final String ARG_DEFINITION = "definition";

    private String word;
    private String definition;

    public VocabularyItemFragment() {
        // Required empty public constructor
    }

    public static VocabularyItemFragment newInstance(String word, String definition) {
        VocabularyItemFragment fragment = new VocabularyItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORD, word);
        args.putString(ARG_DEFINITION, definition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString(ARG_WORD);
            definition = getArguments().getString(ARG_DEFINITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary_item, container, false);

        TextView textViewWord = view.findViewById(R.id.textViewWord);
        TextView textViewDefinition = view.findViewById(R.id.textViewDefinition);

        textViewWord.setText(word);
        textViewDefinition.setText(definition);

        return view;
    }
}
