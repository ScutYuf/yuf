package com.yuf.app.db;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceActivity.Header;

public class MyProvider extends ContentProvider {

	private SQLiteHelper dbHelper;
	// 定义一个UriMatcher类
	private static final UriMatcher MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	private static final int ORDERS = 1;
	private static final int ORDER = 2;
	static {
		MATCHER.addURI("com.yuf.app.myprovider", "orders", ORDERS);
		MATCHER.addURI("com.yuf.app.myprovider", "orders/#", ORDER);

	}
	@Override
	public boolean onCreate() {
		System.out.println("---oncreate----");
		dbHelper = new SQLiteHelper(this.getContext());
		return false;
	}

	// 查询数据
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case ORDERS:
			// 查询所有的数据
			return db.query("orders", projection, selection, selectionArgs,
					null, null, sortOrder);

		case ORDER:
			// 查询某个ID的数据
			// 通过ContentUris这个工具类解释出ID
			long id = ContentUris.parseId(uri);
			String where = " _id=" + id;
			if (!"".equals(selection) && selection != null) {
				where = selection + " and " + where;

			}

			return db.query("orders", projection, where, selectionArgs, null,
					null, sortOrder);
		default:

			throw new IllegalArgumentException("unknow uri" + uri.toString());
		}

	}

	// 返回当前操作的数据的mimeType
	@Override
	public String getType(Uri uri) {
		switch (MATCHER.match(uri)) {
		case ORDERS:
			return "vnd.android.cursor.dir/ORDER";
		case ORDER:
			return "vnd.android.cursor.item/ORDER";
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	// 插入数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri insertUri = null;
		switch (MATCHER.match(uri)) {
		case ORDERS:

			long rowid = db.insert("ORDER", "name", values);
			insertUri = ContentUris.withAppendedId(uri, rowid);

			break;

		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		return insertUri;
	}

	// 删除数据
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		switch (MATCHER.match(uri)) {
		case ORDERS:
			count = db.delete("ORDER", selection, selectionArgs);
			return count;

		case ORDER:
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			count = db.delete("ORDER", where, selectionArgs);
			return count;

		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	// 更新数据
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count = 0;
		switch (MATCHER.match(uri)) {
		case ORDERS:
			count = db.update("ORDER", values, selection, selectionArgs);
			break;
		case ORDER:
			// 通过ContentUri工具类得到ID
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			count = db.update("ORDER", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		return count;
	}

}