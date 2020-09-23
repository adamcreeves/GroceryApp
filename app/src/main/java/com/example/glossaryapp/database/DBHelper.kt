package com.example.glossaryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.glossaryapp.models.Product

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION) {
    var database = writableDatabase

    companion object {
        const val DATA_NAME = "productBD"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "product"
        const val COLUMN_ID = "_id"
        const val COLUMN_PRODUCT_NAME = "productName"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"
        const val createTable =
            "create table $TABLE_NAME ($COLUMN_ID CHAR(24), $COLUMN_PRODUCT_NAME CHAR(50), $COLUMN_PRICE INTEGER, $COLUMN_DESCRIPTION CHAR(200), $COLUMN_IMAGE CHAR(250))"
        const val dropTable = "drop table $TABLE_NAME"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        database?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(dropTable)
        onCreate(database)
    }

    fun addProduct(product: Product) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID, product._id)
        contentValues.put(COLUMN_PRODUCT_NAME, product.productName)
        contentValues.put(COLUMN_PRICE, product.price)
        contentValues.put(COLUMN_DESCRIPTION, product.description)
        contentValues.put(COLUMN_IMAGE, product.image)
        database.insert(TABLE_NAME, null, contentValues)
    }

//    fun updateProduct(product: Product) {
//        val whereClause = "$COLUMN_ID = ?"
//        val whereArgs = arrayOf(product._id)
//        var contentValues = ContentValues()
//
//    }

    fun deleteProduct (_id: String) {
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(_id)
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getProducts() : ArrayList<Product> {
        var productList: ArrayList<Product> = ArrayList()
        var columns = arrayOf(COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_PRICE, COLUMN_DESCRIPTION, COLUMN_IMAGE)
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor !=null && cursor.moveToFirst()) {
            do {
                var _id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var product = Product(_id, null, description, image, null, null, price, productName, null, null, null, null)
                productList.add(product)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

}