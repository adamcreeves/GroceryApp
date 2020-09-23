package com.example.glossaryapp.database

import android.app.TaskStackBuilder
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.Product

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION) {
    var database = writableDatabase

    companion object {
        const val DATA_NAME = "cart_productBD"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cart_product"
        const val COLUMN_ID = "id"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_PRODUCT_NAME = "productName"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val createTable =
            "create table $TABLE_NAME ($COLUMN_ID CHAR(250), $COLUMN_QUANTITY INTEGER, $COLUMN_MRP DECIMAL, $COLUMN_PRODUCT_NAME CHAR(250), $COLUMN_PRICE DECIMAL, $COLUMN_IMAGE CHAR(500))"
        const val dropTable = "drop table $TABLE_NAME"

    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(createTable)
    }

    override fun onUpgrade(database: SQLiteDatabase, p1: Int, p2: Int) {
        database.execSQL(dropTable)
        onCreate(database)
    }

    fun addProduct(cartProductData: CartProductData) {
//        if (isItemInCart(product._id)) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID, cartProductData.id)
        contentValues.put(COLUMN_QUANTITY, cartProductData.quantity)
        contentValues.put(COLUMN_MRP, cartProductData.mrp)
        contentValues.put(COLUMN_PRODUCT_NAME, cartProductData.productName)
        contentValues.put(COLUMN_PRICE, cartProductData.price)
        contentValues.put(COLUMN_IMAGE, cartProductData.image)
        database.insert(TABLE_NAME, null, contentValues)
//        }
    }

    fun updatePlusProduct(cartProductData: CartProductData) {
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(cartProductData.id)
        var contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, (cartProductData.quantity + 1))
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun updateMinusProduct(cartProductData: CartProductData) {
        if (cartProductData.quantity > 0) {
            val whereClause = "$COLUMN_ID = ?"
            val whereArgs = arrayOf(cartProductData.id)
            var contentValues = ContentValues()
            contentValues.put(COLUMN_QUANTITY, (cartProductData.quantity - 1))
            database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        }
    }

    fun isItemInCart(id: String): Boolean {
        val query = "select * from $TABLE_NAME where $COLUMN_ID = ?"
        val cursor = database.rawQuery(query, arrayOf(id))
        var count = cursor.count
        return count != 0
    }

    fun deleteProduct(id: String) {
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id)
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getProducts(): ArrayList<CartProductData> {
        var productList: ArrayList<CartProductData> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_QUANTITY,
            COLUMN_MRP,
            COLUMN_PRODUCT_NAME,
            COLUMN_PRICE,
            COLUMN_IMAGE
        )
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))
                var productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var product = CartProductData(id, quantity, mrp, productName, price, image)
                productList.add(product)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

}