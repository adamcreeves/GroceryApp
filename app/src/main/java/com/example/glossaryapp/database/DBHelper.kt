package com.example.glossaryapp.database


import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.Telephony
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.CartProductData

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION) {
    var database = writableDatabase

    companion object {
        const val DATA_NAME = "cart_productBD"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME_PRODUCT = "cart_product"
        const val COLUMN_ID = "id"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_PRODUCT_NAME = "productName"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val createProductTable =
            "create table $TABLE_NAME_PRODUCT ($COLUMN_ID CHAR(250), $COLUMN_QUANTITY INTEGER, $COLUMN_MRP DECIMAL, $COLUMN_PRODUCT_NAME CHAR(250), $COLUMN_PRICE DECIMAL, $COLUMN_IMAGE CHAR(500))"
        const val dropProductTable = "drop table $TABLE_NAME_PRODUCT"

        const val TABLE_NAME_ADDRESS = "saved_addresses"
        const val COLUMN_PINCODE = "pincode"
        const val COLUMN_HOUSENO = "houseNo"
        const val COLUMN_STREET = "streetName"
        const val COLUMN_CITY = "city"

        const val createSavedAddressTable = "create table $TABLE_NAME_ADDRESS ($COLUMN_PINCODE INTEGER, $COLUMN_HOUSENO CHAR(200), $COLUMN_STREET CHAR(250), $COLUMN_CITY CHAR(250))"
        const val dropAddressTable = "drop table $TABLE_NAME_ADDRESS"


    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(createProductTable)
        database.execSQL(createSavedAddressTable)
    }

    override fun onUpgrade(database: SQLiteDatabase, p1: Int, p2: Int) {
        database.execSQL(dropProductTable)
        onCreate(database)
    }


    fun saveAddress(address: Address) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_PINCODE, address.pincode)
        contentValues.put(COLUMN_HOUSENO, address.houseNo)
        contentValues.put(COLUMN_STREET, address.streetName)
        contentValues.put(COLUMN_CITY, address.city)
        database.insert(TABLE_NAME_ADDRESS, null, contentValues)
    }

    fun getAddress() : Address {
        var columns = arrayOf(
            COLUMN_PINCODE,
            COLUMN_HOUSENO,
            COLUMN_STREET,
            COLUMN_CITY
        )
        var addressData: Address? = null
        var cursor = database.query(TABLE_NAME_ADDRESS, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var pincode = cursor.getInt(cursor.getColumnIndex(COLUMN_PINCODE))
                var houseNo = cursor.getString(cursor.getColumnIndex(COLUMN_HOUSENO))
                var streetName = cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
                var city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                addressData = Address(null, pincode, streetName, city, houseNo,null, null)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return addressData!!
    }

    fun addProduct(cartProductData: CartProductData) {
        val numberOfItemInCart = DatabaseUtils.longForQuery(
            database,
            "select count (*) from $TABLE_NAME_PRODUCT where $COLUMN_ID = '${cartProductData.id}'",
            null
        )

        if (numberOfItemInCart < 1) {
            var contentValues = ContentValues()
            contentValues.put(COLUMN_ID, cartProductData.id)
            contentValues.put(COLUMN_QUANTITY, cartProductData.quantity)
            contentValues.put(COLUMN_MRP, cartProductData.mrp)
            contentValues.put(COLUMN_PRODUCT_NAME, cartProductData.productName)
            contentValues.put(COLUMN_PRICE, cartProductData.price)
            contentValues.put(COLUMN_IMAGE, cartProductData.image)
            database.insert(TABLE_NAME_PRODUCT, null, contentValues)
        } else updatePlusProduct(cartProductData)
    }

    fun updatePlusProduct(cartProductData: CartProductData) {
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(cartProductData.id)
        var contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, (cartProductData.quantity))
        database.update(TABLE_NAME_PRODUCT, contentValues, whereClause, whereArgs)
    }

    fun updateMinusProduct(cartProductData: CartProductData) {
        if (cartProductData.quantity > 0) {
            val whereClause = "$COLUMN_ID = ?"
            val whereArgs = arrayOf(cartProductData.id)
            var contentValues = ContentValues()
            contentValues.put(COLUMN_QUANTITY, (cartProductData.quantity - 1))
            database.update(TABLE_NAME_PRODUCT, contentValues, whereClause, whereArgs)
        }
    }

    fun isItemInCart(id: String): Boolean {
        val query = "select * from $TABLE_NAME_PRODUCT where $COLUMN_ID = ?"
        val cursor = database.rawQuery(query, arrayOf(id))
        var count = cursor.count
        return count != 0
    }

    fun deleteProduct(id: String) {
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id)
        database.delete(TABLE_NAME_PRODUCT, whereClause, whereArgs)
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
        var cursor = database.query(TABLE_NAME_PRODUCT, columns, null, null, null, null, null)
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