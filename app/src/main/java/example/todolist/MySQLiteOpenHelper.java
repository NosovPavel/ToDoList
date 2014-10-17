package example.todolist;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nosovpavel on 17/10/14.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_CREATE = "create table "+ ToDoContentProvider.DATABASE_TABLE + " ("+ToDoContentProvider.KEY_ID + " integer primary key autoincrement, "+
    ToDoContentProvider.KEY_TASK + " text not null,"+ ToDoContentProvider.KEY_CREATION_DATE +"long);";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("TaskDBUpdater","Upgrading from version "+oldVersion+"to new version "+newVersion+"which will destroy all data");
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS"+ToDoContentProvider.DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
