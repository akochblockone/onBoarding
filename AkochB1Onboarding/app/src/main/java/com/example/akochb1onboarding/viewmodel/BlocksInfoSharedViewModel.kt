package com.example.akochb1onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.akochb1onboarding.BlockByIdQuery
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.entity.ErrorBlockResponse
import com.example.akochb1onboarding.domain.usecase.GetChainInfoUseCase
import com.example.akochb1onboarding.domain.usecase.GetLatestBlocksUseCase
import com.example.akochb1onboarding.webapi.WebApiProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BlocksInfoSharedViewModel(
    private val getLatestBlocksUseCase: GetLatestBlocksUseCase,
    private val getChainInfoUseCase: GetChainInfoUseCase
) : ViewModel() {

    private val blockList: MutableList<Block> = mutableListOf()
    private var chainData: ChainInfo? = null

    private var blockBatchFetchSemaphore = false
    private var currPage = 1
    private var currPageProgress = NO_PROGRESS

    private var _blocksLiveData: MutableLiveData<List<Block>> = MutableLiveData()
    val blocksLiveData: LiveData<List<Block>> = _blocksLiveData

    private var _progressLiveData = MutableLiveData(NO_PROGRESS)
    val progressLiveData: LiveData<Int> = _progressLiveData

    private var _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData

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
            val blockResponse = withContext(Dispatchers.IO) {
                getLatestBlocksUseCase.getBlock(blockId)
            }
            if (blockResponse is ErrorBlockResponse) {
                _errorLiveData.value = blockResponse.error
                return@launch
            }
            val block = blockResponse.block ?: return@launch
//            try {
//                val blockGson = block.getAsBlockGson()
//            } catch (e: Exception) {
//                print(block.getPrettyRawBlock())
//                e.printStackTrace()
//                return@launch
//            }

            if (!blockList.contains(block)) {
                blockList.add(block)
                blockList.sortByDescending { it.blockNum }
            }
            _blocksLiveData.value = blockList
            if (block.previousBlock != null) { //&& blockList.size < BLOCK_QTY * currPage) {
                currPageProgress += SINGLE_BLOCK_PROGRESS
                _progressLiveData.value = currPageProgress
                fetchBlock(block.previousBlock)
            } else {
                _progressLiveData.value = FULL_PROGRESS
                currPageProgress = NO_PROGRESS
                blockBatchFetchSemaphore = false
            }
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
            chainData = chainInfo
            if (chainInfo == null) {
                _errorLiveData.value = "Error getting the chain info"
                return@launch
            }
            val headBlockId = chainInfo.headBlockId ?: return@launch
            withContext(Dispatchers.IO) {
                fetchBlock(headBlockId)
            }
        }
    }

    fun testGraphQl() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val apolloClient = WebApiProvider.apolloClient
                val blockByIdQuery = BlockByIdQuery("073c2775aa2fa8ba81207baa5e7b83f800c8cc84fc777dacf817c4e46b512cc3")
                apolloClient.query(blockByIdQuery).enqueue(object : ApolloCall.Callback<BlockByIdQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.e("apollo-call", "error", e)
                    }

                    override fun onResponse(response: Response<BlockByIdQuery.Data>) {
                        val data = response.data?.blockById
                        arrayOf(response.data?.blockById)
                        response.data.toString()
                        print(response.data.toString())
                    }

                })

            }
        }
    }

    fun pauseBlockDownload() {
        blockBatchFetchSemaphore = true
    }

    fun resumeBlockDownload() {
        if (blockList.size % BLOCK_QTY != 0) {
            blockList.lastOrNull()?.previousBlock?.let {
                fetchBlock(it)
            }
        } else {
            blockBatchFetchSemaphore = false
        }
    }

    companion object {
        const val NO_PROGRESS = 0
        const val FULL_PROGRESS = 100
        const val BLOCK_QTY = 30
        const val SINGLE_BLOCK_PROGRESS = ((1 / BLOCK_QTY.toDouble()) * 100).toInt()
    }
}