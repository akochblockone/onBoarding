package com.example.akochb1onboarding.viewmodel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.akochb1onboarding.db.DataBaseProvider
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.usecase.GetChainInfoUseCase
import com.example.akochb1onboarding.domain.usecase.GetLatestBlocksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT)
    private var blockUseCase: GetLatestBlocksUseCase? = null
    private var chainUseCase: GetChainInfoUseCase? = null

    private val blockList: MutableList<Block> = mutableListOf()
    private var chainData: ChainInfo? = null

    private var blockBatchFetchSemaphore = false
    private var currPage = 1
    private var currPageProgress = 0

    private var _blocksLiveData: MutableLiveData<List<Block>> = MutableLiveData()
    var blocksLiveData: LiveData<List<Block>> = _blocksLiveData
        private set

    private val _lastFetchTimeLiveData = MutableLiveData<String>()
    var lastFetchTimeLiveData = _lastFetchTimeLiveData
        private set

    private var _chainLiveData: MutableLiveData<String> = MutableLiveData()
    var chainLiveData: LiveData<String> = _chainLiveData
        private set

    private var _progressLiveData = MutableLiveData<Int>(0)
    var progressLiveData: LiveData<Int> = _progressLiveData
        private set


    fun init(
        getLatestBlocksUseCase: GetLatestBlocksUseCase,
        chainInfoUseCase: GetChainInfoUseCase
    ) {
        this.blockUseCase = getLatestBlocksUseCase
        this.chainUseCase = chainInfoUseCase
    }

    fun fetchMoreBlocks() {
        if (blockBatchFetchSemaphore) return
        currPage++
        currPageProgress = 0
        _progressLiveData.value = currPageProgress
        if (blockList.isNotEmpty()) {
            blockList.lastOrNull()?.previousBlock?.let {
                fetchBlock(it)
            }
        }
    }

    private fun fetchBlock(blockId: String) {
        blockBatchFetchSemaphore = true
        viewModelScope.launch {
            val block = withContext(Dispatchers.IO) {
                blockUseCase?.getBlock(blockId)
            } ?: return@launch
            blockList.add(block)
            _blocksLiveData.value = blockList
            if (block.previousBlock != null && blockList.size < BLOCK_QTY * currPage) {
                currPageProgress = ((blockList.size / (BLOCK_QTY.toDouble() * currPage)) * 100).toInt()
                _progressLiveData.value = currPageProgress
                fetchBlock(block.previousBlock)
            } else {
                _progressLiveData.value = 100
                currPageProgress = 0
                blockBatchFetchSemaphore = false
            }
            _lastFetchTimeLiveData.value = " ${dateFormatter.format(System.currentTimeMillis())}"
        }
    }

    fun fetchChainInfo() {
        blockBatchFetchSemaphore = true
        currPageProgress = 0
        _progressLiveData.value = 0
        blockList.clear()
        _blocksLiveData.value = blockList
        viewModelScope.launch {
            val chainInfo = withContext(Dispatchers.IO) {
                chainUseCase?.getLastChainInfo()
            }
            _chainLiveData.value = chainInfo?.toString() ?: "Info not found"
            chainData = chainInfo
            val headBlockId = chainInfo?.headBlockId ?: return@launch
            withContext(Dispatchers.IO) {
                fetchBlock(headBlockId)
            }
        }
    }

    fun fetchBlocksPaginated(): LiveData<PagedList<BlockEntity>>? {
        val dao = DataBaseProvider.getDataBase().blockDao()
        val factory = dao.getPaged()
        return LivePagedListBuilder(factory, 20).build()
    }

    companion object {
        const val DATE_FORMAT = "dd/MM/yy HH:mm:ss"
        const val BLOCK_QTY = 30
    }
}