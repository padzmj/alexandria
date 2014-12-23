package it.jaschke.alexandria.data;

/**
 * Created by saj on 22/12/14.
 */

import android.provider.BaseColumns;

public class AlexandriaContract{

    public static final class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";

        public static final String TITLE = "title";

        public static final String IMAGE_URL = "imgurl";



    }

    public static final class AuthorEntry implements BaseColumns {
        public static final String TABLE_NAME = "authors";

        public static final String AUTHOR = "author";



    }

    public static final class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "categories";

        public static final String CATEGORY = "category";


    }

}
