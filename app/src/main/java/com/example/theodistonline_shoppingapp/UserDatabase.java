package com.example.theodistonline_shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * SQLite Database Helper for managing user accounts with SHA-256 + random salt password hashing.
 */
public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "theodist_users.db";
    private static final int DATABASE_VERSION = 2; // Incremented version to ensure fresh table & demo seeding

    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_FULL_NAME = "full_name";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_LOCATION = "location";
    private static final String COL_PASSWORD_HASH = "password_hash";
    private static final String COL_SALT = "salt";
    private static final String COL_CREATED_AT = "created_at";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FULL_NAME + " TEXT NOT NULL, "
                + COL_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COL_EMAIL + " TEXT UNIQUE NOT NULL, "
                + COL_PHONE + " TEXT, "
                + COL_LOCATION + " TEXT, "
                + COL_PASSWORD_HASH + " TEXT NOT NULL, "
                + COL_SALT + " TEXT NOT NULL, "
                + COL_CREATED_AT + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Insert default demo users with hashed passwords
        insertDemoUser(db, "Demo Shopper", "shopper", "shopper@theodist.com", "+675 323 0000", "Port Moresby", "1234");
        insertDemoUser(db, "Theo Customer", "theo", "theo@theodist.com", "+675 323 1111", "Lae", "theo123");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertDemoUser(SQLiteDatabase db, String fullName, String username, String email, String phone, String location, String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_LOCATION, location);
        values.put(COL_PASSWORD_HASH, hash);
        values.put(COL_SALT, salt);
        values.put(COL_CREATED_AT, timestamp);
        db.insert(TABLE_USERS, null, values);
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    public String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(hexToBytes(salt));
            byte[] hashBytes = digest.digest(password.getBytes());
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            Log.e("UserDatabase", "SHA-256 algorithm not found", e);
            return "";
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format(Locale.getDefault(), "%02x", b));
        }
        return sb.toString();
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public boolean registerUser(String fullName, String username, String email, String phone, String location, String password) {
        if (usernameExists(username) || emailExists(email)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_LOCATION, location);
        values.put(COL_PASSWORD_HASH, passwordHash);
        values.put(COL_SALT, salt);
        values.put(COL_CREATED_AT, timestamp);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public Customer loginUser(String usernameOrEmail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + " = ? OR " + COL_EMAIL + " = ?";
        String[] selectionArgs = {usernameOrEmail, usernameOrEmail};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FULL_NAME));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION));
            String storedHash = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD_HASH));
            String salt = cursor.getString(cursor.getColumnIndexOrThrow(COL_SALT));
            cursor.close();

            String computedHash = hashPassword(password, salt);
            if (computedHash.equals(storedHash)) {
                return new Customer(id, fullName, username, email, phone, location);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public Customer getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + " = ? OR " + COL_EMAIL + " = ?";
        String[] selectionArgs = {username, username};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FULL_NAME));
            String uname = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION));
            cursor.close();
            return new Customer(id, fullName, uname, email, phone, location);
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_ID}, COL_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_ID}, COL_EMAIL + " = ?", new String[]{email}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }
}
