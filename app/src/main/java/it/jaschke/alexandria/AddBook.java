package it.jaschke.alexandria;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.BookService;


public class AddBook extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText ean;
    private final int LOADER_ID = 1;

    public AddBook(){
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_book, container, false);
        Log.d("my-tag", "onCreateView");

        ean = (EditText) rootView.findViewById(R.id.ean);

        ean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //no need
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<13){
                    return;
                }
                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, s.toString());
                bookIntent.setAction(BookService.FETCH_BOOK);
                getActivity().startService(bookIntent);
                AddBook.this.initLoader();
            }
        });

        return rootView;
    }

    private void initLoader(){
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(ean.getText().length()==0){
            return null;
        }

        return new CursorLoader(
                getActivity(),
                AlexandriaContract.BookEntry.buildFullBookUri(Long.parseLong(ean.getText().toString())),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.v("my-tag", "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }

        String bookTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        ((TextView) getView().findViewById(R.id.bookTitle)).setText(bookTitle);

        String bookSubTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        ((TextView) getView().findViewById(R.id.bookSubTitle)).setText(bookSubTitle);


    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


}
