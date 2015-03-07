package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/6/15.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;

public class ItemOperations {

    // Database fields
    private DataBaseWrapper dbHelper;
    private String[] ITEM_TABLE_COLUMNS = { DataBaseWrapper.ITEM_ID, DataBaseWrapper.ITEM_NAME,
    DataBaseWrapper.ITEM_PRICE};
    private SQLiteDatabase database;

    public ItemOperations(Context context) {
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addItem(ItemObject itemObject) {

        ContentValues values = new ContentValues();

        values.put(DataBaseWrapper.ITEM_NAME, itemObject.getName());
        values.put(DataBaseWrapper.ITEM_PRICE, itemObject.getValue());

        long itemId = database.insert(DataBaseWrapper.ITEMS, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DataBaseWrapper.ITEMS,
                ITEM_TABLE_COLUMNS, DataBaseWrapper.ITEM_ID + " = "
                        + itemId, null, null, null, null);

        cursor.moveToFirst();

        ItemObject newComment = parseItem(cursor);
        cursor.close();
        //return newComment;
    }


    public void deleteItem(ItemObject comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DataBaseWrapper.ITEMS, DataBaseWrapper.ITEM_ID
                + " = " + id, null);
    }

    public int deleteAll(){
        return database.delete(DataBaseWrapper.ITEMS, null, null);
    }

    public List getAll() {
        List items = new ArrayList();

        Cursor cursor = database.query(DataBaseWrapper.ITEMS,
                ITEM_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemObject student = parseItem(cursor);
            items.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    private ItemObject parseItem(Cursor cursor) {
        ItemObject itemObject = new ItemObject();
        itemObject.setId((cursor.getInt(0)));
        itemObject.setName(cursor.getString(1));
        return itemObject;
    }
}
