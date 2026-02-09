package com.bookings.data.repositoryimpl

import android.content.res.AssetManager
import com.bookings.data.entity.BookingDto
import com.bookings.data.mapper.toDomain
import com.bookings.domain.entity.Booking
import com.bookings.domain.repository.AFRepository
import com.bookings.domain.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AFRepositoryImpl @Inject constructor(
    private val assetManager: AssetManager
) : AFRepository {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override suspend fun getBookings(): List<Booking> {
        return withContext(Dispatchers.IO) {
            try {
                assetManager.open(Constants.ASSET_FILE_BOOKINGS)
                    .bufferedReader()
                    .use { it.readText() }
                    .let { jsonString ->
                        val dTos = jsonParser.decodeFromString<List<BookingDto>>(jsonString)
                        dTos.map { it.toDomain() }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getBookingById(id: String): Booking? {
        return getBookings().find { it.id == id }
    }
}