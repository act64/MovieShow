package usst.lei.movieshow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Lei on 2015/11/1.
 */
public class SQLHepler extends SQLiteOpenHelper {
    public SQLHepler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final   String s=String.format("create table T_MyFavourite(%s INTEGER  primary KEY AUTOINCREMENT,%s text not null , " +
                        "%s text not null,%s text not null,%s int not null,%s real not null ," +
                        "unique(%s) on conflict replace); ",
                T_myfa._ID, T_myfa.TITLE,
                T_myfa.DESCRIPTION, T_myfa.POSTERPATH,
                T_myfa.YEAR, T_myfa.VOTEAVE, T_myfa.TITLE);

        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

   public static class T_myfa implements BaseColumns
    {
        public static String Urimake(){
            return "content://"+FavourProvider.Authority+"/"+tablename;
        }



        public static String tablename="T_MyFavourite";
        public static String TITLE="title";
        public static String DESCRIPTION="description";
        public static String VOTEAVE="voteaverage";
        public static String POSTERPATH="posterpath";
        public static String YEAR="m_YEAR";
    }
}
