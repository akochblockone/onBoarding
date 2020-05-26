package com.example.akochb1onboarding.di

import com.example.akochb1onboarding.datasource.GraphQlDataSource
import com.example.akochb1onboarding.datasource.RestDataSource
import com.example.akochb1onboarding.db.DataBaseProvider
import com.example.akochb1onboarding.domain.repository.BlockRepository
import com.example.akochb1onboarding.domain.repository.ChainRepository
import com.example.akochb1onboarding.domain.usecase.GetChainInfoUseCase
import com.example.akochb1onboarding.domain.usecase.GetChainInfoUseCaseImpl
import com.example.akochb1onboarding.domain.usecase.GetLatestBlocksUseCase
import com.example.akochb1onboarding.domain.usecase.GetLatestBlocksUseCaseImpl
import com.example.akochb1onboarding.repository.BlockRepositoryImpl
import com.example.akochb1onboarding.repository.ChainRepositoryImpl
import com.example.akochb1onboarding.viewmodel.BlocksInfoSharedViewModel
import com.example.akochb1onboarding.webapi.EosApi
import com.example.akochb1onboarding.webapi.WebApiProvider
import com.example.akochb1onboarding.webapi.mapper.BlockInfoWebMapper
import com.example.akochb1onboarding.webapi.mapper.ChainInfoWebMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { BlocksInfoSharedViewModel(get(), get()) }
    single<GetLatestBlocksUseCase> { GetLatestBlocksUseCaseImpl(get()) }
    single<GetChainInfoUseCase> { GetChainInfoUseCaseImpl(get()) }
    single<BlockRepository> { BlockRepositoryImpl(GraphQlDataSource(get()), get()) }
//    single<BlockRepository> { BlockRepositoryImpl(RestDataSource(get()), get()) } // use this one for rest endpoint
    single<ChainRepository> { ChainRepositoryImpl(get(), get()) }
    single<EosApi> { WebApiProvider.eosApi }
    single { RestDataSource(get())}
    single { GraphQlDataSource(get()) }
    single { DataBaseProvider.getDataBase().blockDao() }
    factory { ChainInfoWebMapper() }
    factory { BlockInfoWebMapper() }
}
