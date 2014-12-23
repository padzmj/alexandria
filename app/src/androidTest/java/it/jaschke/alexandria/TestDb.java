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
public class TestDb extends AndroidTestCase {
    public static final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(DbHelper.DATABASE_NAME);
        SQLiteDatabase db = new DbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {
        long ean =  9780137903955L;
        String title = "Artificial Intelligence";
        String author = "Stuart Jonathan Russell";
        String imgUrl = "http://books.google.com/books/content?id=KI2WQgAACAAJ&printsec=frontcover&img=1&zoom=1";
        String category = "Computers";

        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlexandriaContract.BookEntry._ID, ean);
        values.put(AlexandriaContract.BookEntry.TITLE, title);
        values.put(AlexandriaContract.BookEntry.IMAGE_URL, imgUrl);

        long retEan = db.insert(AlexandriaContract.BookEntry.TABLE_NAME, null, values);
        assertEquals(ean, retEan);

        String[] columns = {
                AlexandriaContract.BookEntry._ID,
                AlexandriaContract.BookEntry.TITLE,
                AlexandriaContract.BookEntry.IMAGE_URL
        };

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                AlexandriaContract.BookEntry.TABLE_NAME,  // Table to Query
                columns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(AlexandriaContract.BookEntry._ID);
            long id = cursor.getLong(idIndex);

            int titleIndex = cursor.getColumnIndex(AlexandriaContract.BookEntry.TITLE);
            String getTitle = cursor.getString(titleIndex);

            int urlIndex = cursor.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL);
            String url = cursor.getString(urlIndex);

            assertEquals(title, getTitle);
            assertEquals(id, ean);
            assertEquals(url, imgUrl);

        } else {
            fail("No values returned :(");
        }

        // test author table

        values = new ContentValues();
        values.put(AlexandriaContract.AuthorEntry._ID, ean);
        values.put(AlexandriaContract.AuthorEntry.AUTHOR, author);

        retEan = db.insert(AlexandriaContract.AuthorEntry.TABLE_NAME, null, values);
        assertEquals(ean, retEan);

        columns = new String[]{
                AlexandriaContract.AuthorEntry._ID,
                AlexandriaContract.AuthorEntry.AUTHOR
        };

        cursor = db.query(
                AlexandriaContract.AuthorEntry.TABLE_NAME,  // Table to Query
                columns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(AlexandriaContract.AuthorEntry._ID);
            long id = cursor.getLong(idIndex);

            int authorIndex = cursor.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR);
            String getAuthor = cursor.getString(authorIndex);

            assertEquals(author, getAuthor);
            assertEquals(id, ean);

        } else {
            fail("No values returned :(");
        }

        // test category table

        values = new ContentValues();
        values.put(AlexandriaContract.CategoryEntry._ID, ean);
        values.put(AlexandriaContract.CategoryEntry.CATEGORY, category);

        retEan = db.insert(AlexandriaContract.CategoryEntry.TABLE_NAME, null, values);
        assertEquals(ean, retEan);

        columns = new String[]{
                AlexandriaContract.CategoryEntry._ID,
                AlexandriaContract.CategoryEntry.CATEGORY
        };

        cursor = db.query(
                AlexandriaContract.CategoryEntry.TABLE_NAME,  // Table to Query
                columns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(AlexandriaContract.CategoryEntry._ID);
            long id = cursor.getLong(idIndex);

            int catIndex = cursor.getColumnIndex(AlexandriaContract.CategoryEntry.CATEGORY);
            String getCat = cursor.getString(catIndex);
            assertEquals("categorie name",category, getCat);
            assertEquals("categorie ean/id",id, ean);

        } else {
            fail("No values returned :(");
        }

        dbHelper.close();

    }
}