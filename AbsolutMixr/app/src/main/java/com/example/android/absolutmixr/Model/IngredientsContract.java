package com.example.android.absolutmixr.Model;

import android.provider.BaseColumns;

/**
 * Created by Danny on 7/31/2017.
 */

public class IngredientsContract {
    public static class TABLE_ARTICLES implements BaseColumns {
        //column names are self explanatory
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }
}
