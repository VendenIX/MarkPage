package fr.MarkPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Map;

public class VocabularyListAdapter extends BaseAdapter {
    private Book currentBook;
    private LayoutInflater inflater;

    public VocabularyListAdapter(Context context, Book currentBook) {
        this.currentBook = currentBook;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (currentBook != null && currentBook.getVocabulary() != null) {
            return 1; // Il y a seulement un livre à afficher
        } else {
            return 0;
        }
    }

    @Override
    public Book getItem(int position) {
        return currentBook;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_vocabulary_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewWord = convertView.findViewById(R.id.textViewWord);
            viewHolder.textViewDefinition = convertView.findViewById(R.id.textViewDefinition);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book book = getItem(position);
        Map<String, String> vocabulary = book.getVocabulary();

        StringBuilder vocabularyText = new StringBuilder();
        for (Map.Entry<String, String> entry : vocabulary.entrySet()) {
            vocabularyText.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }

        viewHolder.textViewWord.setText(book.getTitle());
        viewHolder.textViewDefinition.setText(vocabularyText.toString());

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewWord;
        TextView textViewDefinition;
    }
}
