package com.example.akochb1onboarding.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.domain.entity.ChainInfo
import com.example.akochb1onboarding.domain.usecase.GetChainInfoUseCase
import com.example.akochb1onboarding.domain.usecase.GetLatestBlocksUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class MainActivityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getLatestBlocksUseCase = mockk<GetLatestBlocksUseCase>()
    private val chainInfoUseCase = mockk<GetChainInfoUseCase>()
    private val viewModel = BlocksInfoSharedViewModel(getLatestBlocksUseCase, chainInfoUseCase)

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        every { getLatestBlocksUseCase.getBlock(HEAD_BLOCK_ID) } returns Block()
        every { chainInfoUseCase.getLastChainInfo() } returns ChainInfo(headBlockId = HEAD_BLOCK_ID)
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchBlock() {
        runBlocking {
            viewModel.fetchChainInfo()
            verify { getLatestBlocksUseCase.getBlock(HEAD_BLOCK_ID) }
            assert(viewModel.blocksLiveData.value?.isNotEmpty() == true)
        }
    }

    @Test
    fun fetchChainInfo() {
        runBlocking {
            viewModel.fetchChainInfo()
            verify { chainInfoUseCase.getLastChainInfo() }
            assert(viewModel.chainLiveData.value != null)
        }
    }

    companion object {
        const val HEAD_BLOCK_ID = "123"
    }
}