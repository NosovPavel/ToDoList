package example.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by nosovpavel on 17/10/14.
 */
public class ToDoContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI= Uri.parse("content://example.todolist.ToDoContentProvider/todoitems");
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_CREATION_DATE = "creation_date";

    public static final String DATABASE_NAME = "todoDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "todoItemTable";

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static UriMatcher uriMatcher = null;

    private MySQLiteOpenHelper myOpenHelper;

    @Override
    public boolean onCreate() {
        myOpenHelper = new MySQLiteOpenHelper(getContext(),DATABASE_NAME,null,DATABASE_VERSION);

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("example.todolist.ToDoContentProvider","todoitems",ALLROWS);
        uriMatcher.addURI("example.todolist.ToDoContentProvider","todoitems/#",SINGLE_ROW);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        //При необходимости надо заменить следующие значения корректными
        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DATABASE_TABLE);

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:{
                String rowId = uri.getPathSegments().get(1);
                sqLiteQueryBuilder.appendWhere(KEY_ID+"="+rowId);
            }
            break;
            default:break;
        }

        Cursor cursor = sqLiteQueryBuilder.query(db,projection,selection,selectionArgs,groupBy,having,sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String returnValue = "";
        switch (uriMatcher.match(uri)){
            case ALLROWS:{
                returnValue = "vnd.android.cursor.dir/vnd.paad.todos";
            }
            break;
            case SINGLE_ROW:{
                returnValue = "vnd.android.cursor.item/vnd.paad.todos";
            }
            break;
            default:{
                throw new IllegalArgumentException("Unsupported URI:"+uri);
            }
        }
        return returnValue;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        //  Чтобы  добавить  в  базу  данных  пустую  строку  с  помощью  пустого
        //  объекта  ContentValues,  используйте  параметр  nullColumnHack,
        //  указав  название  столбца,  значение  которого  может  равняться  null.
        String  nullColumnHack  =  null;
        long id = db.insert(DATABASE_TABLE,nullColumnHack,values);

        if(id>-1){
            //  Создайте  и  верните  путь  URI  к  только  что  вставленной  строке.
            Uri  insertedld  =  ContentUris.withAppendedId(CONTENT_URI, id);
            //  Оповестите  все  объекты  ContentObserver  об  изменениях
            //в  наборе  данных.
            getContext().getContentResolver().notifyChange(insertedld,  null);
            return  insertedld;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        //Если это путь к строке, удаляем только указанную строку
        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:{
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID+"="+rowId+(!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
            }
            break;
            default:break;
        }

        //  Чтобы  вернуть  количество  удаленных  элементов, укажите  оператор  WHERE.
        //  Чтобы  удалить  все  строки  и  вернуть  значение,  передайте  "1".
        if  (selection == null){
            selection = "1";
        }

        //Выполняем удаление
        int deleteCount = db.delete(DATABASE_TABLE,selection,selectionArgs);

        getContext().getContentResolver().notifyChange(uri,null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        //Если это путь к строке, обновляем только указанную строку
        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:{
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID+"="+rowId+(!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
            }
            break;
            default:break;
        }

        //  Выполните  обновление.
        int  updateCount  =  db.update(DATABASE_TABLE,
                values,  selection,  selectionArgs);
        //  Оповестите  все  объекты  ContentObserver  об  изменениях
        //  в  наборе  данных.
        getContext().getContentResolver().notifyChange(uri, null);
        return  updateCount;
    }
}
