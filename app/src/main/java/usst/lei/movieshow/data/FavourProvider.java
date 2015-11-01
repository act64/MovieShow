package usst.lei.movieshow.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import usst.lei.movieshow.data.SQLHepler.T_myfa;

/**
 * Created by Lei on 2015/11/1.
 */
public class FavourProvider extends ContentProvider {
    static SQLHepler hepler;
   static UriMatcher matcher;
    final static String Authority = "usst.lei.movieshow.data.provider";
    final static int TableAll = 1;
    final static int TableItem = 2;
static
{

    matcher = new UriMatcher(UriMatcher.NO_MATCH);
    matcher.addURI(Authority, SQLHepler.T_myfa.tablename, TableAll);
    matcher.addURI(Authority, T_myfa.tablename + "/*", TableItem);
}
    @Override
    public boolean onCreate() {
        Log.e("task","creatUri");
        hepler = new SQLHepler(getContext().getApplicationContext(), "myFav.db", null, 1);
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case TableAll: {
                SQLiteDatabase db = hepler.getReadableDatabase();
                Cursor cursor =db.query(T_myfa.tablename,null,selection,selectionArgs,null,null,sortOrder);
                db.close();
                return cursor;
            }
            case TableItem:{
                SQLiteDatabase db = hepler.getReadableDatabase();
                Cursor cursor =db.query(T_myfa.tablename,null,T_myfa.TITLE+"=?",new String[]{uri.getPathSegments().get(1)},null,null,sortOrder);
                db.close();
                return cursor;
            }
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case TableAll:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + Authority + "." + T_myfa.tablename;
            case TableItem:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + Authority + "." + T_myfa.tablename;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e("task",uri.toString());
        switch (matcher.match(uri)) {
            case TableAll:
            case TableItem:
                SQLiteDatabase datebae = hepler.getWritableDatabase();
                long rows = datebae.insert(T_myfa.tablename, null, values);
                datebae.close();
                if (rows > 0) {
                    return Uri.parse("content://" + Authority + "/" + T_myfa.tablename + "/" + values.getAsString(T_myfa.TITLE));
                }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (matcher.match(uri)) {
            case TableItem:
                SQLiteDatabase db = hepler.getWritableDatabase();
                db.delete(T_myfa.tablename, T_myfa.TITLE + "=?", new String[]{uri.getPathSegments().get(1)});
                db.close();
                return 1;
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
