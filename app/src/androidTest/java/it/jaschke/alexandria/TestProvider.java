package it.jaschke.alexandria;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.data.DbHelper;

/**
 * Created by saj on 23/12/14.
 */
public class TestProvider extends AndroidTestCase {
    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void setUp() {
        deleteAllRecords();
    }

    public void deleteAllRecords() {
        mContext.getContentResolver().delete(
                AlexandriaContract.BookEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                AlexandriaContract.CategoryEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                AlexandriaContract.AuthorEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                AlexandriaContract.BookEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                AlexandriaContract.AuthorEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                AlexandriaContract.CategoryEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    public void testGetType() {

        String type = mContext.getContentResolver().getType(AlexandriaContract.BookEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.BookEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.AuthorEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.AuthorEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.CategoryEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.CategoryEntry.CONTENT_TYPE, type);

        long id = 9780137903955L;
        type = mContext.getContentResolver().getType(AlexandriaContract.BookEntry.buildBookUri(id));
        assertEquals(AlexandriaContract.BookEntry.CONTENT_ITEM_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.AuthorEntry.buildAuthorUri(id));
        assertEquals(AlexandriaContract.AuthorEntry.CONTENT_ITEM_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.CategoryEntry.buildCategoryUri(id));
        assertEquals(AlexandriaContract.CategoryEntry.CONTENT_ITEM_TYPE, type);

    }
}