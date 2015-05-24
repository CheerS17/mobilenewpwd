package com.example.mobileonepwd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.mobileonepwd.entity.Info;
import com.example.mobileonepwd.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CheerS17 on 5/20/15.
 */
public class UserDataManager {

    private static final String TAG = "UserDataManager";
    private static final String DB_NAME = "user_data";

    private static final String TABLE_NAME_USER = "users";
    private static final String TABLE_NAME_INFO = "info";

    public static final String UID = "uid";
    public static final String ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String PHONE = "phone";
    public static final String EASYPWD = "easypwd";
    public static final String SITENAME = "sitename";
    public static final String SITE_USERNAME = "site_username";
    public static final String L_COUNT = "l_count";
    public static final String N_COUNT = "n_count";
    public static final String P_COUNT = "p_count";
    public static final String TOKEN = "token";
    public static final String SILENT = "silent";
    public static final String VIBRATE = "vibrate";

    private static final int DB_VERSION = 2;
    private Context mContext = null;

    private static final String DB_CREATE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " ("
            + UID + " integer primary key autoincrement," + PHONE + " varchar,"
            + TOKEN + " varchar" + ");";

    private static final String DB_CREATE_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_INFO + " ("
            + ID + " integer primary key autoincrement ," + PHONE + " varchar , " + SITENAME + " varchar ," + SITE_USERNAME + " varchar,"
            + L_COUNT + " integer, " + N_COUNT + " integer, " + P_COUNT + " integer " + ");";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "db.getVersion()=" + db.getVersion());
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER + ";");
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INFO + ";");
            db.execSQL(DB_CREATE_USER);
            db.execSQL(DB_CREATE_INFO);
            Log.i(TAG, "db.execSQL(DB_CREATE)");
            Log.e(TAG, DB_CREATE_USER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            onCreate(db);
        }
    }

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");
    }

    public void openDataBase() throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {

        mDatabaseHelper.close();
    }

    /**
     * @param userData
     * @return
     */
    public long insertUserData(User userData) {

        String phone = userData.getPhone();
        String token = userData.getToken();


        ContentValues values = new ContentValues();
        values.put(PHONE, phone);
        values.put(TOKEN, token);


        return mSQLiteDatabase.insert(TABLE_NAME_USER, UID, values);
    }

    /**
     * @param userData
     * @param phone
     * @return
     */
    public long insertInfo(Info userData, String phone) {
        String sitename = userData.getSitename();
        String siteUsername = userData.getSiteUsername();
        int lCount = userData.getLCount();
        int nCount = userData.getNCount();
        int pCount = userData.getPCount();

        ContentValues values = new ContentValues();
        values.put(SITENAME, sitename);
        values.put(SITE_USERNAME, siteUsername);
        values.put(PHONE, phone);
        values.put(L_COUNT, new Integer(lCount));
        values.put(N_COUNT, new Integer(nCount));
        values.put(P_COUNT, new Integer(pCount));

        return mSQLiteDatabase.insert(TABLE_NAME_INFO, ID, values);
    }

    public boolean updateUserData(User userData) {

        int uid = userData.getUid();
        String userName = userData.getPhone();
        String token = userData.getToken();

        ContentValues values = new ContentValues();
        values.put(PHONE, userName);
        values.put(TOKEN, token);
        return mSQLiteDatabase.update(TABLE_NAME_USER, values, UID + "=" + uid, null) > 0;
    }

    public Cursor fetchUserData(int id) throws SQLException {

        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_USER, null, ID
                + "=" + id, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllUserDatas() {

        return mSQLiteDatabase.query(TABLE_NAME_USER, null, null, null, null, null,
                null);
    }

    /**
     *
     * @param sitename
     * @param siteUsername
     * @return
     */
    public boolean deleteUserData(String sitename, String siteUsername) {

        return mSQLiteDatabase.delete(TABLE_NAME_INFO,SITENAME + "= ? and " + SITE_USERNAME + " = ? ", new String[] {sitename , siteUsername}) > 0;
    }

    /**
     * @return
     */
    public void deleteAllUserDatas() {

        mSQLiteDatabase.execSQL("delete from " + TABLE_NAME_USER+";");
    }


    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }


    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME_USER, values, ID + "=" + id, null) > 0;
    }

    /**
     * @return
     */
    public User getUser() {
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME_USER, null, null, null, null, null, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                //获得ID

                user.setUid(cursor.getInt(0));
                user.setPhone(cursor.getString(1));
                user.setToken(cursor.getString(2));
            }
        }

        return user;
    }

    /**
     * @param phone
     * @return
     */
    public List<Info> getInfoByPhone(String phone) {
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME_INFO, null, PHONE + "=" + phone, null, null, null, null);
        List<Info> infoList = new ArrayList<Info>();

        while (cursor.moveToNext()) {
            Info info = new Info();
            //获得ID
            info.setId(cursor.getInt(0));
            info.setPhone(cursor.getString(1));
            info.setSitename(cursor.getString(2));
            info.setSiteUsername(cursor.getString(3));
            info.setLCount(cursor.getInt(4));
            info.setNCount(cursor.getInt(5));
            info.setPCount(cursor.getInt(6));

            infoList.add(info);
            //输出用户信息 System.out.println(id+":"+sname+":"+snumber);
        }

        return infoList;
    }


}
