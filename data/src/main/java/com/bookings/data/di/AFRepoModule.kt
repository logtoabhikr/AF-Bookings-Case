package com.bookings.data.di

import com.bookings.data.repositoryimpl.AFRepositoryImpl
import com.bookings.domain.repository.AFRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AFRepoModule {
    @Binds
    fun bindAFRepositoryImpl(afRepositoryImpl: AFRepositoryImpl): AFRepository
}