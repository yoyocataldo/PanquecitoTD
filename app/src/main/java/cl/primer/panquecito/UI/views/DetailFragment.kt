package cl.primer.panquecito.UI.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cl.primer.panquecito.R
import cl.primer.panquecito.UI.viewmodel.ProductVM
import cl.primer.panquecito.databinding.FragmentDetailBinding
import coil.load


class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    private val productVM: ProductVM by activityViewModels()



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater)
        val product = productVM.getSelected()
        productVM.consumeDetail(product.id)
        productVM.getDetail(product.id).observe(viewLifecycleOwner, {
            it?.let {
                binding.imgProducDetail.load(it.image)
                binding.productNameDetail.text = it.title
                binding.tvPriceProductDetail.text = it.price.toString()
                binding.txtProductDetail.text = it.detailDescription
            }
        })

        return binding.root
    }
}