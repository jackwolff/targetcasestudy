package com.target.targetcasestudy.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.target.targetcasestudy.R
import com.target.targetcasestudy.database.dataobjects.ProductResponse
import com.target.targetcasestudy.navigation.NavDispatch
import com.target.targetcasestudy.ui.ProductItemsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject constructor(
    private val targetDatabase: TargetDatabase,
    private val dispatch: NavDispatch,
    @ApplicationContext
    context: Context
) : ViewModel() {
    
    public val dealsAdapter = ProductItemsAdapter(::onProductSelected)
    private val errorImg = BitmapFactory.decodeResource(context.resources, R.drawable.img_not_found)

    private val currentState = MutableStateFlow<DealListState>(DealListState.Loading)
    val dealListState: Flow<DealListState>
        get() = currentState

    var selectedProduct: ProductItemsAdapter.ProductInfo? = null
        private set

    init {
        loadDeals()
    }

    private fun loadDeals() {
        viewModelScope.launch {
            when(val dealsList = targetDatabase.getProductList()) {
                is JSONResult.Success<*> -> {
                    currentState.value = DealListState.Success
                    loadItemsIntoAdapter(dealsList.value!! as ProductResponse)
                }
                else -> {
                    currentState.value = DealListState.Failed
                }
            }
        }
    }

    private suspend fun loadItemsIntoAdapter(products: ProductResponse) {
        dealsAdapter.setProductList(products)
        products.products.forEach { product ->
            viewModelScope.launch {
                val img = getImageFromURL(product.imageUrl)
                dealsAdapter.setProductImage(img, product.id)
            }
        }
    }

    private suspend fun getImageFromURL(url: String?): Bitmap {
        return withContext(Dispatchers.IO) {
            if(url != null)
                Picasso.get().load(url).get()
            else
                errorImg
        }
    }

    private fun onProductSelected(product: ProductItemsAdapter.ProductInfo) {
        selectedProduct = product
        dispatch.navigate(R.id.navigate_to_deal_item_fragment)
    }
}

public sealed class DealListState {
    public object Loading : DealListState()
    public object Failed  : DealListState()
    public object Success : DealListState()
}