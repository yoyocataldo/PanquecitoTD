package cl.primer.panquecito.Domain.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cl.primer.panquecito.Data.model.Product
import cl.primer.panquecito.Data.model.ProductDetail
import cl.primer.panquecito.Data.remote.RetrofitClient
import cl.primer.panquecito.Domain.db.ProductApplication
import cl.primer.panquecito.Domain.db.productDetailEntity
import cl.primer.panquecito.Domain.db.productEntity


class Repository {

    private val productDB = ProductApplication.db!!.productDao()

    fun productList(): LiveData<List<Product>> = Transformations.map(productDB.getProducts()) {
        it.map { entity ->
            db2api(entity)
        }
    }

    fun getDetailFromDB(pid: Int): LiveData<ProductDetail> = Transformations.map(productDB.getDetail(pid)) {
        it?.let { detailEntity ->
            db2api(detailEntity)
        }
    }

    suspend fun getProducts() {
        val response = RetrofitClient
            .RetrofitInstance()
                .getCakes()

        response.let {
            when(it.isSuccessful) {
                true -> {
                    response.body()?.let { productList ->
                        val map = productList.map { product ->
                            api2db(product)
                        }
                        productDB.insertProducts(map)
                    }
                }
                false -> {
                    Log.d("Repo", "error en Repo")
                }
            }
        }
    }

    suspend fun getDetail(id: Int) {
        val response = RetrofitClient.RetrofitInstance().getCake(id.toString())

        if (response.isSuccessful) {
            response.body()?.let { detail ->
                productDB.insertDetail(api2db(detail))
            }
        } else {
            Log.d("Repo", "error en el detalle ${response.code()}")
        }
    }
}

fun api2db(product: Product): productEntity {
    return productEntity(product.id, product.title, product.previewDescription, product.size, product.price, product.image)
}

fun db2api(productEntity: productEntity): Product {
    return Product(productEntity.id, productEntity.title, productEntity.previewDescription, productEntity.size, productEntity.price, productEntity.image )
}

fun api2db(detail: ProductDetail): productDetailEntity {
    return productDetailEntity(detail.id, detail.title, detail.previewDescription, detail.detailDescription, detail.image, detail.shape, detail.size, detail.price, detail.lastPrice, detail.delivery)
}

fun db2api(detailEntity: productDetailEntity): ProductDetail {
    return ProductDetail(detailEntity.id, detailEntity.title, detailEntity.previewDescription, detailEntity.detailDescription, detailEntity.image, detailEntity.shape, detailEntity.size, detailEntity.price, detailEntity.lastPrice, detailEntity.delivery)
}
