package com.example.glossaryapp.app

class Endpoints {

    companion object {

        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_CATEGORY = "category"
        private const val URL_SUBCATEGORY = "subcategory"
        private const val URL_PRODUCTS_BY_SUB = "products/sub"
        private const val URL_PRODUCT = "products"


        fun getLogin() : String {
            return Configure.BASE_URL + URL_LOGIN
        }

        fun getRegister() : String {
            return Configure.BASE_URL + URL_REGISTER
        }

        fun getCategory() : String {
            return Configure.BASE_URL + URL_CATEGORY
        }

        fun getSubcategoryByCatId(catId: Int) : String {
            return "${Configure.BASE_URL + URL_SUBCATEGORY}/$catId"
        }

        fun getProductBySubId(subId: Int) : String {
            return "${Configure.BASE_URL + URL_PRODUCTS_BY_SUB}/${subId}"
        }

        fun getProductByProductId(productId: String?) : String {
            return "${Configure.BASE_URL + URL_PRODUCT}/$productId"
        }

    }

}