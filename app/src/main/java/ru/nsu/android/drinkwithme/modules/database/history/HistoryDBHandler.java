package ru.nsu.android.drinkwithme.modules.database.history;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.android.drinkwithme.model.DrinkLiter;

public class HistoryDBHandler extends SQLiteOpenHelper implements IHistoryDBHandler {
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "history";
    private static final String KEY_ID = "id";
    private static final String KEY_GROUP_ID = "group_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PERCENT = "percent";
    private static final String KEY_LITER = "liter";

    private static final String GROUP_TABLE_NAME = "group_table";
    private static final String GROUP_KEY = "group_number";

    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + KEY_GROUP_ID + "," + KEY_NAME + "," + KEY_PERCENT + "," + KEY_LITER + ") VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_GROUP_ID + "=?";

    private static final String SELECT_GROUP = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_GROUP_ID + "=?";

    public HistoryDBHandler(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!tableExist(db)) {
            createTable(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void add(DrinkLiter drink) {
        SQLiteDatabase db = this.getWritableDatabase();
        String groupId = Long.toString(getGroupId(db));
        db.execSQL(INSERT, new String[] {groupId, drink.getName(), Integer.toString(drink.getPercent()), Double.toString(drink.getLiter())});
    }

    @Override
    public boolean nextGroup() {
        SQLiteDatabase db = this.getWritableDatabase();
        long currentId = getGroupId(db);
        boolean flag = false;
        if (getGroup(db, currentId).size() != 0) {
            String UPDATE_GROUP = "UPDATE " + GROUP_TABLE_NAME + " SET " + GROUP_KEY + "=?";
            db.execSQL(UPDATE_GROUP, new String[] {Long.toString(currentId + 1)});
            flag = true;
        }
        db.close();
        return flag;
    }

    @Override
    public List<DrinkLiter> getGroup(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<DrinkLiter> result = getGroup(db, id);
        db.close();
        return result;
    }

    private List<DrinkLiter> getGroup(SQLiteDatabase db, long id) {
        List<DrinkLiter> result = new LinkedList<>();
        Cursor cursor = db.rawQuery(SELECT_GROUP, new String[] {Long.toString(id)});
        if (cursor.moveToNext()) {
            do {
                result.add(new DrinkLiter(cursor.getLong(0),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @Override
    public List<List<DrinkLiter>> getAll() {
        List<List<DrinkLiter>> result = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        long currentId = getGroupId(db);
        for (long i = 0; i <= currentId; ++i) {
            result.add(getGroup(db, i));
        }
        db.close();
        return result;
    }

    @Override
    public void deleteGroup(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String groupId = Long.toString(getGroupId(db));
        db.execSQL(DELETE, new String[] {groupId});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);
        db.close();
    }

    private long getGroupId(SQLiteDatabase db) {
        String SELECT_GROUP = "SELECT * FROM " + GROUP_TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_GROUP, null);
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            return id;
        }
        return 0;
    }

    private boolean tableExist(SQLiteDatabase db) {
        try {
            db.execSQL("SELECT * FROM " + TABLE_NAME);
            db.execSQL("SELECT * FROM " + GROUP_TABLE_NAME);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void createTable(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_GROUP_ID + " INTEGER,"
                + KEY_NAME + " TEXT,"
                + KEY_PERCENT + " INTEGER,"
                + KEY_LITER + " REAL" + ")";
        db.execSQL(CREATE_TABLE);
        String CREATE_GROUP_TABLE = "CREATE TABLE " + GROUP_TABLE_NAME + "("
                + GROUP_KEY + " INTEGER" + ")";
        db.execSQL(CREATE_GROUP_TABLE);
        String INSET_FIRST_GROUP = "INSERT INTO " + GROUP_TABLE_NAME + "(" + GROUP_KEY + ") VALUES(?)";
        db.execSQL(INSET_FIRST_GROUP, new String[] {Long.toString(0)});
    }
}
