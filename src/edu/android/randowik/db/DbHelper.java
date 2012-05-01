package edu.android.randowik.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper {
	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "randowik.db";
	private static final String TABLE_NAME = "pages";

	public static final String PAGES_COLUMN_ID = "_id";
	public static final String PAGES_COLUMN_PAGE_DATETIME = "page_datetime";
	public static final String PAGES_COLUMN_TITLE = "title";
	public static final String PAGES_COLUMN_CONTENT = "content";
	public static final String PAGES_COLUMN_URL = "page_url";

	private RandoWikOpenHelper openHelper;
	private SQLiteDatabase database;

	public DbHelper(Context context) {
		openHelper = new RandoWikOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}

	public void close() {
		if (database.isOpen()) {
			this.database.close();
		}
	}

	public void savePage(Date date, String pageId, String pageTitle, String pageContent, String pageUrl) {
		if (date == null)
			date = new Date();
		ContentValues contentValues = new ContentValues();
		contentValues.put(PAGES_COLUMN_TITLE, pageTitle);
		contentValues.put(PAGES_COLUMN_CONTENT, pageContent);
		String dateAsString = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
		contentValues.put(PAGES_COLUMN_PAGE_DATETIME, dateAsString);
		contentValues.put(PAGES_COLUMN_URL, pageUrl);
		database.insert(TABLE_NAME, null, contentValues);
	}

	public Cursor getAllPages() {
		return database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	}

	public Cursor findPageByPageId(String id) {
		return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + PAGES_COLUMN_ID + " = ?", new String[] { id });
	}
	
	public void deleteById(String id) {
		database.delete(TABLE_NAME, " "+PAGES_COLUMN_ID + " = ?", new String[] {id});
	}

	private class RandoWikOpenHelper extends SQLiteOpenHelper {
		RandoWikOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE " + TABLE_NAME + " (" + PAGES_COLUMN_ID + " INTEGER PRIMARY KEY, " + 
					PAGES_COLUMN_PAGE_DATETIME + " TEXT, " + 
					PAGES_COLUMN_TITLE + " TEXT, " + 
					PAGES_COLUMN_CONTENT + " TEXT, " + 
					PAGES_COLUMN_URL + " TEXT "+")";
			log("RandoWikOpenHelper.onCreate " + sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			log("RandoWikOpenHelper.onUpgrade " + sql);
			db.execSQL(sql);
			onCreate(db);
		}
	}

	private static void log(String s) {
		System.out.println(s);
	}
}
