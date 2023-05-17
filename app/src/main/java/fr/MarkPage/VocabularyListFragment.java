package fr.MarkPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VocabularyListFragment extends Fragment {

    private ListView listViewVocabulary;
    private VocabularyListAdapter vocabularyListAdapter;
    private Book currentBook; // Ajoutez une variable pour le livre en cours

    public VocabularyListFragment() {
        // Required empty public constructor
    }

    public static VocabularyListFragment newInstance() {
        return new VocabularyListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary_list, container, false);
        listViewVocabulary = view.findViewById(R.id.listViewVocabulary);
        vocabularyListAdapter = new VocabularyListAdapter(requireContext(), currentBook); // Passez le livre en cours à VocabularyListAdapter
        listViewVocabulary.setAdapter(vocabularyListAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Vous pouvez effectuer d'autres opérations ici, telles que la mise à jour de la liste de vocabulaire avec les données appropriées
    }
}