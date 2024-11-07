package fpoly.account.ph56774_thi19.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.account.ph56774_thi19.Database.DBHelper;
import fpoly.account.ph56774_thi19.Model.SanPham;

public class SanPhamDAO {
    private DBHelper helper;
    private SQLiteDatabase database;

    public SanPhamDAO(Context context) {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    public boolean add(SanPham sp){
        database = helper.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(helper.COLUMN_TITLE, sp.getTitle());
            values.put(helper.COLUMN_CONTENT, sp.getContent());
            values.put(helper.COLUMN_DATE, sp.getDate());
            values.put(helper.COLUMN_STYLE, sp.getStyle());
            values.put(helper.COLUMN_SRC, sp.getSrc()); // Thêm giá trị cho cột SRC

            long check = database.insert(helper.TABLE_SANPHAM_NAME, null, values);
            if (check != -1){
                database.setTransactionSuccessful();
                Log.d("SanPhamDAO", "SRC added: " + sp.getSrc());
            }
            return check != -1;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            database.endTransaction();
        }
    }

    public boolean update(SanPham sp){
        database = helper.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(helper.COLUMN_TITLE, sp.getTitle());
            values.put(helper.COLUMN_CONTENT, sp.getContent());
            values.put(helper.COLUMN_DATE, sp.getDate());
            values.put(helper.COLUMN_STYLE, sp.getStyle());
            values.put(helper.COLUMN_SRC, sp.getSrc()); // Cập nhật giá trị cho cột SRC

            int rows = database.update(helper.TABLE_SANPHAM_NAME, values, helper.COLUMN_ID + " = ?", new String[]{String.valueOf(sp.getId())});
            if (rows > 0 ){
                database.setTransactionSuccessful();
            }
            return rows > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            database.endTransaction();
        }
    }

    public boolean delete(int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            int rows = sqLiteDatabase.delete(helper.TABLE_SANPHAM_NAME, helper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            if (rows > 0) {
                sqLiteDatabase.setTransactionSuccessful();
            }
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @SuppressLint("Range")
    public ArrayList<SanPham> getSP(){
        ArrayList<SanPham> lst = new ArrayList<>();
        database = helper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + helper.TABLE_SANPHAM_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    SanPham todoModel = new SanPham();
                    todoModel.setId(cursor.getInt(cursor.getColumnIndex(helper.COLUMN_ID)));
                    todoModel.setTitle(cursor.getString(cursor.getColumnIndex(helper.COLUMN_TITLE)));
                    todoModel.setContent(cursor.getString(cursor.getColumnIndex(helper.COLUMN_CONTENT)));
                    todoModel.setDate(cursor.getString(cursor.getColumnIndex(helper.COLUMN_DATE)));
                    todoModel.setStyle(cursor.getString(cursor.getColumnIndex(helper.COLUMN_STYLE)));
                    todoModel.setSrc(cursor.getString(cursor.getColumnIndex(helper.COLUMN_SRC))); // Lấy giá trị cột SRC
                    lst.add(todoModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lst;
    }
}
