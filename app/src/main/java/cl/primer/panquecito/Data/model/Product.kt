package cl.primer.panquecito.Data.model

data class Product(
    val id: Int,
    val title: String,
    val previewDescription: String,
    val size: String,
    val price: Long,
    val image: String
)
