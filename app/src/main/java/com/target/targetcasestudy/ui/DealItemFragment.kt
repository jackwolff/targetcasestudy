package com.target.targetcasestudy.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.target.targetcasestudy.R
import com.target.targetcasestudy.database.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deal_item.*

@AndroidEntryPoint
class DealItemFragment : Fragment() {

  private lateinit var productViewModel: ProductViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    productViewModel = ViewModelProvider(this.requireActivity()).get(ProductViewModel::class.java)
    return inflater.inflate(R.layout.fragment_deal_item, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val product = productViewModel.selectedProduct!!.product
    val bitmap  = productViewModel.selectedProduct!!.bitmap!!

    product_img.setImageBitmap(bitmap)
    regular_price.text = resources.getString(R.string.regular_price, product.regularPrice.displayString)

    if(product.salePrice != null) {
      special_price.text = product.salePrice.displayString
      regular_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or regular_price.paintFlags
    } else {
      special_price.visibility = TextView.INVISIBLE
      regular_price.paintFlags = regular_price.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }

    product_description_txt.text = product.description
    product_title.text = product.title
  }
}
