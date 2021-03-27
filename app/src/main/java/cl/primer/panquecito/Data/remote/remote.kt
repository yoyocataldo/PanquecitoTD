package cl.primer.panquecito.Data.remote


import cl.primer.panquecito.Data.model.Product
import cl.primer.panquecito.Data.model.ProductDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//https://my-json-server.typicode.com/Himuravidal/FakeAPIdata/products/
//http://my-json-server.typicode.com/Himuravidal/FakeAPIdata/details/1

interface ProductAPI {
    @GET("cakes")
    suspend fun getCakes(): Response<List<Product>>

    @GET("cakeDetails/{pid}")
    suspend fun getCake(@Path("pid") id: String): Response<ProductDetail>

}

class RetrofitClient{
    companion object{
        private const val BASE_URL = "https://my-json-server.typicode.com/Himuravidal/cakesApi/"
        fun RetrofitInstance(): ProductAPI {
            val retrofit = Retrofit
                .Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                    .create()).build()
            return retrofit.create(ProductAPI::class.java)

        }

    }

}