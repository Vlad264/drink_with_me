package ru.nsu.android.drinkwithme.modules.database.parameters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.nsu.android.drinkwithme.R;

public class ParametersDBHandler extends SQLiteOpenHelper implements IParametersDBHandler {
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "parameters";
    private static final String KEY_TYPE = "type";
    private static final String KEY_VALUE = "value";

    private static final String TYPE_NAME = "name";
    private static final String TYPE_WEIGHT = "weight";
    private static final String TYPE_HEIGHT = "height";
    private static final String TYPE_GENDER = "gender";

    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + KEY_TYPE + "," + KEY_VALUE + ") VALUES(?, ?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + KEY_VALUE + "=? WHERE " + KEY_TYPE + "=?";

    private static final String SELECT_TYPE = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TYPE + "=?";

    private Context context;

    public ParametersDBHandler(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!tableExist(db)) {
            createTableAndStartValues(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void setName(String name) {
        updateType(TYPE_NAME, name);
    }

    @Override
    public String getName() {
        return getType(TYPE_NAME);
    }

    @Override
    public void setWeight(int weight) {
        updateType(TYPE_WEIGHT, Integer.toString(weight));
    }

    @Override
    public int getWeight() {
        String result = getType(TYPE_WEIGHT);
        if (result == null) {
            return -1;
        }
        return Integer.parseInt(result);
    }

    @Override
    public void setHeight(int height) {
        updateType(TYPE_HEIGHT, Integer.toString(height));
    }

    @Override
    public int getHeight() {
        String result = getType(TYPE_HEIGHT);
        if (result == null) {
            return -1;
        }
        return Integer.parseInt(result);
    }

    @Override
    public void setGender(String gender) {
        updateType(TYPE_GENDER, gender);
    }

    @Override
    public String getGender() {
        return getType(TYPE_GENDER);
    }

    private void updateType(String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(UPDATE, new String[] {value, type});
        db.close();
    }

    private String getType(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_TYPE, new String[] { type });
        if (cursor.moveToNext()) {
            String result = cursor.getString(1);
            cursor.close();
            db.close();
            return result;
        }
        cursor.close();
        db.close();
        return null;
    }

    private boolean tableExist(SQLiteDatabase db) {
        try {
            db.execSQL("SELECT * FROM " + TABLE_NAME);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void createTableAndStartValues(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_TYPE + " TEXT NOT NULL UNIQUE,"
                + KEY_VALUE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL(INSERT, new String[] {TYPE_NAME, context.getString(R.string.default_name)});
        db.execSQL(INSERT, new String[] {TYPE_WEIGHT, context.getString(R.string.default_weight)});
        db.execSQL(INSERT, new String[] {TYPE_HEIGHT, context.getString(R.string.default_height)});
        db.execSQL(INSERT, new String[] {TYPE_GENDER, context.getString(R.string.default_gender)});
    }
}
