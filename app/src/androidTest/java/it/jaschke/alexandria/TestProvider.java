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

    public void testDeleteDb() throws Throwable {
        mContext.deleteDatabase(DbHelper.DATABASE_NAME);
    }

    public void testInsertReadProvider() {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long ean =  9780137903955L;
        String title = "Artificial Intelligence";
        String author = "Stuart Jonathan Russell";
        String imgUrl = "http://books.google.com/books/content?id=KI2WQgAACAAJ&printsec=frontcover&img=1&zoom=1";
        String category = "Computers";


        // test book-query
        ContentValues values = new ContentValues();
        values.put(AlexandriaContract.BookEntry._ID, ean);
        values.put(AlexandriaContract.BookEntry.TITLE, title);
        values.put(AlexandriaContract.BookEntry.IMAGE_URL, imgUrl);

        long retEan = db.insert(AlexandriaContract.BookEntry.TABLE_NAME, null, values);
        assertEquals(ean, retEan);

        Cursor cursor = mContext.getContentResolver().query(
                AlexandriaContract.BookEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(cursor, values);

        //test author query
        values = new ContentValues();
        values.put(AlexandriaContract.AuthorEntry._ID, ean);
        values.put(AlexandriaContract.AuthorEntry.AUTHOR, author);

        retEan = db.insert(AlexandriaContract.AuthorEntry.TABLE_NAME, null, values);

        cursor = mContext.getContentResolver().query(
                AlexandriaContract.AuthorEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(cursor, values);

        //test category query
        values = new ContentValues();
        values.put(AlexandriaContract.CategoryEntry._ID, ean);
        values.put(AlexandriaContract.CategoryEntry.CATEGORY, category);

        retEan = db.insert(AlexandriaContract.CategoryEntry.TABLE_NAME, null, values);

        cursor = mContext.getContentResolver().query(
                AlexandriaContract.CategoryEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(cursor, values);

    }

    public void testGetType() {

        String type = mContext.getContentResolver().getType(AlexandriaContract.BookEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.BookEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.AuthorEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.AuthorEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(AlexandriaContract.CategoryEntry.CONTENT_URI);
        assertEquals(AlexandriaContract.CategoryEntry.CONTENT_TYPE, type);


    }
}