package fr.MarkPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<Book> {
    private int resourceLayout;
    private Context mContext;

    public BookListAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Book book = getItem(position);

        if(book != null) {
            TextView titleText = (TextView) v.findViewById(R.id.book_title);
            TextView authorText = (TextView) v.findViewById(R.id.book_author);
            ProgressBar progress = (ProgressBar) v.findViewById(R.id.book_progress);

            if(titleText != null) {
                int percentage = (int) ((book.getCurrentPage() * 100.0) / book.getTotalPages());
                titleText.setText(book.getTitle());
            }

            if(authorText != null) {
                authorText.setText(book.getAuthor() );
            }

            if(progress != null) {
                progress.setMax(book.getTotalPages());
                progress.setProgress(book.getCurrentPage());
            }
        }
        return v;
    }
}