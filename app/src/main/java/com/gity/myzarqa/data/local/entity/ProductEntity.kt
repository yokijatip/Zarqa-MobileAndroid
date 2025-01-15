package com.gity.myzarqa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
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
)
