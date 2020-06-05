package ru.nsu.android.drinkwithme.modules.database.history;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static final String STATE_TABLE_NAME = "state";
    private static final String STATE_KEY_GROUP = "group_id";
    private static final String STATE_KEY_STATE = "state_result";

    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(" + KEY_GROUP_ID + "," + KEY_NAME + "," + KEY_PERCENT + "," + KEY_LITER + ") VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_GROUP_ID + "=?";

    private static final String SELECT_GROUP = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_GROUP_ID + "=?";

    private AtomicInteger countOpen = new AtomicInteger(0);
    private SQLiteDatabase writableDatabase;

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
        SQLiteDatabase db = openDataBase();
        String groupId = Long.toString(getGroupId());
        db.execSQL(INSERT, new String[] {groupId, drink.getName(), Integer.toString(drink.getPercent()), Double.toString(drink.getLiter())});
        closeDataBase();
    }

    @Override
    public boolean nextGroup() {
        SQLiteDatabase db = openDataBase();
        long currentId = getGroupId();
        boolean flag = false;
        if (getGroup(currentId).size() != 0) {
            String UPDATE_GROUP = "UPDATE " + GROUP_TABLE_NAME + " SET " + GROUP_KEY + "=?";
            db.execSQL(UPDATE_GROUP, new String[] {Long.toString(currentId + 1)});
            flag = true;
        }
        closeDataBase();
        return flag;
    }

    @Override
    public void setState(int state) {
        SQLiteDatabase db = openDataBase();
        String groupId = Long.toString(getGroupId());
        db.execSQL("DELETE FROM " + STATE_TABLE_NAME + " WHERE " + STATE_KEY_GROUP + "=?", new String[] {groupId});
        db.execSQL("INSERT INTO " + STATE_TABLE_NAME + "(" + STATE_KEY_GROUP + "," + STATE_KEY_STATE + ") VALUES(?, ?)", new String[] {groupId, Integer.toString(state)});
        closeDataBase();
    }

    @Override
    public List<DrinkLiter> getGroup(long id) {
        SQLiteDatabase db = openDataBase();
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
        closeDataBase();
        return result;
    }

    @Override
    public List<DrinkLiter> getLastGroup() {
        SQLiteDatabase db = openDataBase();
        long currentId = getGroupId();
        List<DrinkLiter> result = getGroup(currentId);
        closeDataBase();
        return result;
    }

    @Override
    public List<List<DrinkLiter>> getAll() {
        List<List<DrinkLiter>> result = new LinkedList<>();
        SQLiteDatabase db = openDataBase();
        long currentId = getGroupId();
        for (long i = 0; i <= currentId; ++i) {
            result.add(getGroup(i));
        }
        closeDataBase();
        return result;
    }

    @Override
    public int getState(long id) {
        SQLiteDatabase db = openDataBase();
        String SELECT_GROUP = "SELECT * FROM " + STATE_TABLE_NAME + " WHERE " + STATE_KEY_GROUP + "=?";
        Cursor cursor = db.rawQuery(SELECT_GROUP, new String[] {Long.toString(id)});
        if (cursor.moveToFirst()) {
            int state = cursor.getInt(1);
            cursor.close();
            closeDataBase();
            return state;
        }
        cursor.close();
        closeDataBase();
        return -1;
    }

    @Override
    public int getLastState() {
        long currentId = getGroupId();
        return getState(currentId);
    }

    @Override
    public List<Integer> getAllStates() {
        List<Integer> result = new LinkedList<>();
        SQLiteDatabase db = openDataBase();
        long currentId = getGroupId();
        for (long i = 0; i <= currentId; ++i) {
            result.add(getState(i));
        }
        closeDataBase();
        return result;
    }

    @Override
    public void deleteGroup(long id) {
        SQLiteDatabase db = openDataBase();
        String groupId = Long.toString(getGroupId());
        db.execSQL(DELETE, new String[] {groupId});
        closeDataBase();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = openDataBase();
        onUpgrade(db, 0, 0);
        closeDataBase();
    }

    private long getGroupId() {
        SQLiteDatabase db = openDataBase();
        String SELECT_GROUP = "SELECT * FROM " + GROUP_TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_GROUP, null);
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            cursor.close();
            closeDataBase();
            return id;
        }
        cursor.close();
        closeDataBase();
        return 0;
    }

    private boolean tableExist(SQLiteDatabase db) {
        try {
            db.execSQL("SELECT * FROM " + TABLE_NAME);
            db.execSQL("SELECT * FROM " + GROUP_TABLE_NAME);
            db.execSQL("SELECT * FROM " + STATE_TABLE_NAME);
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

        String CREATE_STATE_TABLE = "CREATE TABLE " + STATE_TABLE_NAME + "("
                + STATE_KEY_GROUP + " INTEGER PRIMARY KEY,"
                + STATE_KEY_STATE + " INTEGER" + ")";
        db.execSQL(CREATE_STATE_TABLE);
    }

    private SQLiteDatabase openDataBase() {
        synchronized (this) {
            if (countOpen.incrementAndGet() == 1) {
                writableDatabase = getWritableDatabase();
            }
        }
        return writableDatabase;
    }

    private void closeDataBase() {
        if (countOpen.decrementAndGet() == 0) {
            writableDatabase.close();
        }
    }
}
