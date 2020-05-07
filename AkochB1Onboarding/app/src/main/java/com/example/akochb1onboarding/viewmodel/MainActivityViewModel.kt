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

class MainActivityViewModel(
    private val getLatestBlocksUseCase: GetLatestBlocksUseCase,
    private val getChainInfoUseCase: GetChainInfoUseCase
) : ViewModel() {

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT)


    private val blockList: MutableList<Block> = mutableListOf()
    private var chainData: ChainInfo? = null

    private var blockBatchFetchSemaphore = false
    private var currPage = 1
    private var currPageProgress = NO_PROGRESS

    private var _blocksLiveData: MutableLiveData<List<Block>> = MutableLiveData()
    var blocksLiveData: LiveData<List<Block>> = _blocksLiveData
        private set

    private val _lastFetchTimeLiveData = MutableLiveData<String>()
    var lastFetchTimeLiveData = _lastFetchTimeLiveData
        private set

    private var _chainLiveData: MutableLiveData<String> = MutableLiveData()
    var chainLiveData: LiveData<String> = _chainLiveData
        private set

    private var _progressLiveData = MutableLiveData<Int>(NO_PROGRESS)
    var progressLiveData: LiveData<Int> = _progressLiveData
        private set

    fun fetchMoreBlocks() {
        if (blockBatchFetchSemaphore) return
        currPage++
        currPageProgress = NO_PROGRESS
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
                getLatestBlocksUseCase.getBlock(blockId)
            } ?: return@launch
            blockList.add(block)
            _blocksLiveData.value = blockList
            if (block.previousBlock != null && blockList.size < BLOCK_QTY * currPage) {
                currPageProgress += SINGLE_BLOCK_PROGRESS
                _progressLiveData.value = currPageProgress
                fetchBlock(block.previousBlock)
            } else {
                _progressLiveData.value = FULL_PROGRESS
                currPageProgress = NO_PROGRESS
                blockBatchFetchSemaphore = false
            }
            _lastFetchTimeLiveData.value = " ${dateFormatter.format(System.currentTimeMillis())}"
        }
    }

    fun fetchChainInfo() {
        blockBatchFetchSemaphore = true
        currPageProgress = NO_PROGRESS
        _progressLiveData.value = NO_PROGRESS
        blockList.clear()
        _blocksLiveData.value = blockList
        viewModelScope.launch {
            val chainInfo = withContext(Dispatchers.IO) {
                getChainInfoUseCase.getLastChainInfo()
            }
            _chainLiveData.value = chainInfo?.toString() ?: NO_CHAIN
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
        return LivePagedListBuilder(factory, BLOCK_QTY).build()
    }

    companion object {
        const val NO_PROGRESS = 0
        const val FULL_PROGRESS = 100
        const val NO_CHAIN = "Info not found"
        const val DATE_FORMAT = "dd/MM/yy HH:mm:ss"
        const val BLOCK_QTY = 30
        const val SINGLE_BLOCK_PROGRESS = ((1 / BLOCK_QTY.toDouble()) * 100).toInt()
    }
}