package com.example.shopify.model

data class Location(
    val Id:Int=0,
    val Name:String=""

){
    override fun toString(): String {
        return Name
    }
}
