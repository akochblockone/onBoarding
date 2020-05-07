package com.example.akochb1onboarding.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.akochb1onboarding.databinding.ActivityMainBinding
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.ui.BlockListAdapter
import com.example.akochb1onboarding.viewmodel.MainActivityViewModel
import org.koin.android.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    private val adapter = BlockListAdapter()
    private val onItemClick: (block: Block) -> Unit = {
        val intent = BlockDetailActivity.getIntent(this, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = getViewModel() // koin "by viewModel()" won't work
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // code for paginated stuff (not working)
//        binding.fetchButton.setOnClickListener {
//            viewModel.fetchBlocksPaginated()?.observe(this, Observer {
//                adapter.submitList(it)
//            })
//        }

        viewModel.blocksLiveData.observeForever {
            adapter.submitList(it)
        }
        adapter.onClickCallback = onItemClick
        initRecycler(binding.blocksRecyclerView)
        binding.fetchButton.setOnClickListener {
            adapter.submitList(listOf())
            viewModel.fetchChainInfo()
        }
    }

    private fun initRecycler(recycler: RecyclerView) {
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        // "Infinite list" listener, we fetch more blocks once we reach the end of the list
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recycler.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems: Int = recycler.adapter?.itemCount ?: 0
                if (numItems > 1 && pos >= numItems - 1) {
                    viewModel.fetchMoreBlocks()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onDestroy() {
        adapter.clearListener()
        super.onDestroy()
    }
}
