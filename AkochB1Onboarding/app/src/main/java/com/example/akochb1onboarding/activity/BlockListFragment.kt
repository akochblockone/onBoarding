package com.example.akochb1onboarding.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.akochb1onboarding.R
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.ui.BlockListAdapter
import com.example.akochb1onboarding.viewmodel.BlocksInfoSharedViewModel
import kotlinx.android.synthetic.main.fragment_block_list.fetchButton
import kotlinx.android.synthetic.main.fragment_block_list.blocksRecyclerView
import kotlinx.android.synthetic.main.fragment_block_list.progressBar
import org.koin.android.viewmodel.ext.android.getViewModel

class BlockListFragment : Fragment(R.layout.fragment_block_list) {

    private var viewModel: BlocksInfoSharedViewModel? = null
    private lateinit var adapter: BlockListAdapter
    private var navigation: NavController? = null

    private val onItemClick: (block: Block) -> Unit = {
        navigation?.navigate(
            BlockListFragmentDirections.actionBlockListFragmentToBlockDetailFragment(it)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        adapter = BlockListAdapter()
        adapter.onClickCallback = onItemClick
        navigation = findNavController()
        initBlockListAdapter(blocksRecyclerView)
        initClickListener(fetchButton)
        initProgressBar(progressBar)
        initErrorListener()
    }

    private fun initErrorListener() {
        viewModel?.errorLiveData?.observe(viewLifecycleOwner, Observer{
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onPause() {
        viewModel?.pauseBlockDownload()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.resumeBlockDownload()
    }

    private fun initClickListener(button: Button) {
        button.setOnClickListener {
            button.isEnabled = false
            viewModel?.fetchChainInfo()
        }
    }

    private fun initProgressBar(progressBar: ProgressBar) {
        viewModel?.progressLiveData?.observeForever {
            progressBar.progress = it
        }
    }

    private fun initBlockListAdapter(recycler: RecyclerView) {
        val context = this@BlockListFragment.context
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        viewModel?.blocksLiveData?.observe(viewLifecycleOwner, Observer {
            fetchButton.isEnabled = true
            // A ListAdapter needs a mutable list to work properly
            adapter.submitList(it.toMutableList())
        })
        // "Infinite list" listener, we fetch more blocks once we reach the end of the list
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recycler.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems: Int = recycler.adapter?.itemCount ?: 0
                if (numItems > 1 && pos >= numItems - 1) {
                    viewModel?.fetchMoreBlocks()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onDestroyView() {
        viewModel?.progressLiveData?.removeObservers(this)
        viewModel = null
        navigation = null
        adapter.clearListener()
        super.onDestroyView()
    }
}