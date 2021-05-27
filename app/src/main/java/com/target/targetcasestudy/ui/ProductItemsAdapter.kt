package com.target.targetcasestudy.ui

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.database.dataobjects.Product
import com.target.targetcasestudy.database.dataobjects.ProductResponse
import timber.log.Timber

class ProductItemsAdapter(
  private val onItemSelected: (product: ProductInfo) -> Unit
) : RecyclerView.Adapter<ProductItemsAdapter.DealItemViewHolder>() {

    private val products: MutableList<ProductInfo> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val view = inflater.inflate(R.layout.deal_list_item, parent, false)
      return DealItemViewHolder(view)
  }

  override fun getItemCount(): Int {
      return products.size
  }

  fun setProductList(newProductList: ProductResponse) {
      products.clear()
      products.addAll(newProductList.products.map { ProductInfo(it, null) })
      notifyDataSetChanged()
  }

  fun setProductImage(bitmap: Bitmap, id: Int) {
      val index = products.indexOfFirst { it.product.id == id }
      val product = products[index]
      product.bitmap = bitmap
      notifyItemChanged(index)
  }

  override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
      val item = products[position].product
      viewHolder.productTitle.text = item.title
      viewHolder.productPrice.text = if(item.salePrice != null) item.salePrice.displayString else item.regularPrice.displayString
      viewHolder.aisle.text        = item.aisle
      val bitmap = products[position].bitmap
      if(bitmap != null) {
          viewHolder.progressBar.visibility = ProgressBar.GONE
          viewHolder.img.setImageBitmap(bitmap)
          viewHolder.img.visibility = ImageView.VISIBLE
      } else {
          viewHolder.progressBar.visibility = ProgressBar.VISIBLE
          viewHolder.img.setImageBitmap(null)
          viewHolder.img.visibility = ImageView.GONE
      }
  }

  inner class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
      val productTitle: TextView      = itemView.findViewById<TextView>(R.id.deal_list_item_title)
      val productPrice: TextView      = itemView.findViewById<TextView>(R.id.deal_list_item_price)
      val aisle:        TextView      = itemView.findViewById<TextView>(R.id.aisle_txt)
      val img:          ImageView     = itemView.findViewById<ImageView>(R.id.product_img)
      val progressBar:  ProgressBar   = itemView.findViewById<ProgressBar>(R.id.product_progress_bar)

      init {
          itemView.setOnClickListener(this)
      }

      override fun onClick(v: View?) {
        onItemSelected(products[adapterPosition])
      }
  }

  public data class ProductInfo(
      val product: Product,
      var bitmap: Bitmap?
  )
}