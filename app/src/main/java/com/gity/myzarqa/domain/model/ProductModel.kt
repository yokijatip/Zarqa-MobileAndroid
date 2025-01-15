package com.gity.myzarqa.domain.model


data class ProductModel (
    val id: Long = 0,
    val name: String,
    val description: String? = null,
    val size: String,
    val price: Int,
    val stock: Int,
    val minStock: Int,
    val category: String,
    val color: String,
    val productImageUrl: String? = null,
//    val createdAt: String? = null,
//    val updateAt: String? = null
)