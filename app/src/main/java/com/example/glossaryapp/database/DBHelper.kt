package com.example.glossaryapp.database


import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.Telephony
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.OrderSummary

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

        const val createSavedAddressTable =
            "create table $TABLE_NAME_ADDRESS ($COLUMN_PINCODE INTEGER, $COLUMN_HOUSENO CHAR(200), $COLUMN_STREET CHAR(250), $COLUMN_CITY CHAR(250))"
        const val dropAddressTable = "drop table $TABLE_NAME_ADDRESS"

        const val TABLE_NAME_TOTALS = "checkout_totals"
        const val COLUMN_SUBTOTAL = "subtotal"
        const val COLUMN_DISCOUNT = "discount"
        const val COLUMN_DELIVERY_CHARGES = "delivery_charges"
        const val COLUMN_TOTAL = "total"
        const val COLUMN_ORDER_AMOUNT = "order_amount"

        const val createProductTotalsTable =
            "create table $TABLE_NAME_TOTALS ($COLUMN_SUBTOTAL CHAR(200), $COLUMN_DISCOUNT CHAR(200), $COLUMN_DELIVERY_CHARGES CHAR(200), $COLUMN_TOTAL CHAR(200), $COLUMN_ORDER_AMOUNT CHAR(200))"
        const val dropProductTotalsTable = "drop table $TABLE_NAME_TOTALS"

    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(createProductTable)
        database.execSQL(createSavedAddressTable)
        database.execSQL(createProductTotalsTable)
    }

    override fun onUpgrade(database: SQLiteDatabase, p1: Int, p2: Int) {
        database.execSQL(dropProductTable)
        database.execSQL(dropAddressTable)
        database.execSQL(dropProductTotalsTable)
        onCreate(database)
    }

    fun saveCurrentTotals(subtotal: String, discount: String, total: String) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_SUBTOTAL, subtotal)
        contentValues.put(COLUMN_DISCOUNT, discount)
        contentValues.put(COLUMN_TOTAL, total)
        if (total.toDouble() < 300.0) {
            contentValues.put(COLUMN_DELIVERY_CHARGES, "$50.0")
            contentValues.put(COLUMN_ORDER_AMOUNT, (total.toDouble() + 50.0).toString())
        } else {
            contentValues.put(COLUMN_DELIVERY_CHARGES, "$0.0")
            contentValues.put(COLUMN_ORDER_AMOUNT, total)
        }
        database.insert(TABLE_NAME_TOTALS, null, contentValues)
    }

fun getOrderSummary() : Array<String> {
    var columns = arrayOf(
        COLUMN_SUBTOTAL,
        COLUMN_DISCOUNT,
        COLUMN_TOTAL,
        COLUMN_DELIVERY_CHARGES,
        COLUMN_ORDER_AMOUNT
    )
    var currentTotals: Array<String>? = null
    var cursor = database.query(TABLE_NAME_TOTALS, columns, null, null, null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        do {
            var ourPrice = cursor.getString(cursor.getColumnIndex(COLUMN_SUBTOTAL))
            var discount = cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT))
            var totalAmount = cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL))
            var deliveryCharges = cursor.getString(cursor.getColumnIndex(COLUMN_DELIVERY_CHARGES))
            var orderAmount = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_AMOUNT))
            currentTotals = arrayOf(deliveryCharges.toString(), discount.toString(), orderAmount.toString(), ourPrice.toString(), totalAmount.toString())
        } while (cursor.moveToNext())
    }
    cursor.close()
    return currentTotals!!
}

    fun saveAddress(address: Address) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_PINCODE, address.pincode)
        contentValues.put(COLUMN_HOUSENO, address.houseNo)
        contentValues.put(COLUMN_STREET, address.streetName)
        contentValues.put(COLUMN_CITY, address.city)
        database.insert(TABLE_NAME_ADDRESS, null, contentValues)
    }

    fun getAddress(): Address {
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
                addressData = Address(null, pincode, streetName, city, houseNo, null, null)
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