package cl.primer.panquecito.Domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class productEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val previewDescription: String,
    val size: String,
    val price: Long,
    val image: String


)
@Entity(tableName = "productsDetail")
data class productDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val previewDescription: String,
    val detailDescription: String,
    val image: String,
    val shape: String,
    val size: String,
    val price: Long,
    val  lastPrice: Long,
    val delivery: Boolean
)