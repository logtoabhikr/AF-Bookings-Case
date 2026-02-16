package com.bookings.data.di

import com.bookings.data.repositoryimpl.AFRepositoryImpl
import com.bookings.domain.repository.AFRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AFRepoModule {
    @Binds
    @Singleton
    fun bindAFRepositoryImpl(afRepositoryImpl: AFRepositoryImpl): AFRepository
}