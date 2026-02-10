package com.bookings.data.repositoryimpl

import android.content.res.AssetManager
import com.bookings.data.entity.BookingDto
import com.bookings.data.mapper.toDomain
import com.bookings.domain.entity.Booking
import com.bookings.domain.repository.AFRepository
import com.bookings.domain.utils.Constants
import com.bookings.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AFRepositoryImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val jsonParser: Json,
) : AFRepository {

    override suspend fun getBookings(): Flow<Result<List<Booking>>> = flow {
        try {
            emit(Result.Loading)
            val bookings = assetManager.open(Constants.ASSET_FILE_BOOKINGS)
                .bufferedReader()
                .use { it.readText() }
                .let { jsonParser.decodeFromString<List<BookingDto>>(it) }
                .map { it.toDomain() }
            if (bookings.isNotEmpty()) {
                emit(Result.Success(bookings))
            } else {
                emit(Result.Error(""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error("${e.message}", e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getBookingById(id: String): Flow<Result<Booking>> {
        return getBookings().map { result ->
            when (result) {
                is Result.Success -> {
                    val booking = result.data.find { it.id == id }
                    if (booking != null) {
                        Result.Success(booking)
                    } else {
                        Result.Error("")
                    }
                }

                is Result.Error -> Result.Error(result.message, result.exception)
                is Result.Loading -> Result.Loading
            }
        }
    }
}