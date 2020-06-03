package ru.nsu.android.drinkwithme.modules.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.android.drinkwithme.model.Drink;

public class DrinkDBHandler extends SQLiteOpenHelper implements IDrinkDBHandler {
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "drinks";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PERCENT = "percent";

    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + KEY_NAME + "," + KEY_PERCENT + ") VALUES(?, ?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + KEY_NAME + "=?," + KEY_PERCENT + "=? WHERE " + KEY_ID + "=?";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=?";

    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=?";
    private static final String SELECT_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + "=?";

    public DrinkDBHandler(Context context) {
        super(context, TABLE_NAME, null, VERSION);
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
    public void add(Drink drink) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT, new String[] {drink.getName(), Integer.toString(drink.getPercent())});
        db.close();
    }

    @Override
    public void update(long id, Drink drink) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(UPDATE, new String[] {drink.getName(), Integer.toString(drink.getPercent()), Long.toString(id)});
        db.close();
    }

    @Override
    public long has(Drink drink) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_NAME, new String[] {drink.getName()});
        if (cursor.moveToNext()) {
            long id = Long.parseLong(cursor.getString(0));
            cursor.close();
            db.close();
            return id;
        }
        cursor.close();
        db.close();
        return -1;
    }

    @Override
    public Drink get(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ID, new String[] { Long.toString(id) });
        if (cursor.moveToNext()) {
            Drink result = new Drink(Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)));
            cursor.close();
            db.close();
            return result;
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public List<Drink> getAll() {
        List<Drink> result = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(new Drink(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE, new String[] {Long.toString(id)});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.close();
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
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_NAME + " TEXT,"
                + KEY_PERCENT + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }
}
