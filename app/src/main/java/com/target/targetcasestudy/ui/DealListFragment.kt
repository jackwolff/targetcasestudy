package com.target.targetcasestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.target.targetcasestudy.R
import com.target.targetcasestudy.database.DealListState
import com.target.targetcasestudy.database.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deal_list.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DealListFragment : Fragment() {

  private lateinit var productViewModel: ProductViewModel

  private lateinit var progressBar: ProgressBar
  private lateinit var errorText: TextView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    productViewModel = ViewModelProvider(this.requireActivity()).get(ProductViewModel::class.java)
    return inflater.inflate(R.layout.fragment_deal_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    progressBar = deal_list_progress_bar
    errorText = deal_list_error_text

    lifecycleScope.launchWhenCreated {
      productViewModel.dealListState.collect {
        updateState(it)
      }
    }

    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    recyclerView.adapter = productViewModel.dealsAdapter
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
  }

  private fun updateState(dealListState: DealListState) {
    when(dealListState) {
      DealListState.Success -> {
        progressBar.visibility = ProgressBar.GONE
        errorText.visibility = TextView.GONE
      }
      DealListState.Loading -> {
        progressBar.visibility = ProgressBar.VISIBLE
        errorText.visibility = TextView.GONE
      }
      DealListState.Failed -> {
        progressBar.visibility = ProgressBar.INVISIBLE
        errorText.visibility = TextView.VISIBLE
      }
    }
  }
}
