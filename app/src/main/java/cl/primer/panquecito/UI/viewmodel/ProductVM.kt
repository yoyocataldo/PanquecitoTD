package cl.primer.panquecito.UI.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.primer.panquecito.Data.model.Product
import cl.primer.panquecito.Data.model.ProductDetail
import cl.primer.panquecito.Domain.repo.Repository
import kotlinx.coroutines.launch

class ProductVM: ViewModel() {
    private val repo = Repository()

    fun producList (): LiveData<List<Product>> = repo.productList()
    fun getProducts(){
        viewModelScope.launch {
            repo.getProducts()
        }


    }

    fun getDetail(id: Int): LiveData<ProductDetail> = repo.getDetailFromDB(id)
    fun consumeDetail (id: Int){
        viewModelScope.launch { repo.getDetail(id) }


    }


    private lateinit var selected: Product
    fun setSelected(product:Product){
        selected = product

    }

    fun getSelected() = selected
}
