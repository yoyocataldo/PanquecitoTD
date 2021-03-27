package cl.primer.panquecito.UI.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import cl.primer.panquecito.R
import cl.primer.panquecito.UI.adapter.ProductAdapter
import cl.primer.panquecito.UI.viewmodel.ProductVM
import cl.primer.panquecito.databinding.FragmentListingBinding


class ListingFragment : Fragment() {


    class ListingFragment : Fragment() {

        private lateinit var binding: FragmentListingBinding
        private val productVM: ProductVM by activityViewModels()


        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
            binding = FragmentListingBinding.inflate(inflater)

            val adapter = ProductAdapter()
            binding.productList.adapter = adapter
            binding.productList.layoutManager = GridLayoutManager(context, 1)

            binding.productList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

            productVM.producList().observe(viewLifecycleOwner, {
                adapter.update(it)
            })

            adapter.selectedItem().observe(viewLifecycleOwner, {
                productVM.setSelected(it)

                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_container, DetailFragment())
                        ?.addToBackStack("detail")?.commit()
            })
            return binding.root
        }
    }
}

