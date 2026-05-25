package com.junfengtech.bankingdemo.di

import com.junfengtech.bankingdemo.data.repository.MockTransactionRepository
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: MockTransactionRepository
    ): TransactionRepository
}