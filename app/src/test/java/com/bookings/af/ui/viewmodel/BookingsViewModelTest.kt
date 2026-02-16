package com.bookings.af.ui.viewmodel

import app.cash.turbine.test
import com.bookings.af.ui.viewstate.BookingsUiState
import com.bookings.domain.usecase.BookingUseCase
import com.bookings.domain.utils.Constants
import com.bookings.domain.utils.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BookingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val bookingUseCase = mockk<BookingUseCase>()
    private lateinit var viewModel: BookingsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadBookings state to Success when UseCase returns booking`() = runTest {

        val mockBookings = listOf(Constants.createMockBooking("1"))
        every { bookingUseCase() } returns flowOf(Result.Success(mockBookings))

        viewModel = BookingsViewModel(bookingUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = expectMostRecentItem()
            println("Current State: $state")
            assert(state is BookingsUiState.Success)
            assertEquals(mockBookings, (state as BookingsUiState.Success).bookings)
            assertEquals("1", state.bookings[0].id)
        }
    }

    @Test
    fun `loadBookings state to Error when UseCase fails`() = runTest {

        val expectedErrorMsg = "Something went wrong"
        every { bookingUseCase() } returns flowOf(Result.Error(expectedErrorMsg))

        viewModel = BookingsViewModel(bookingUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = expectMostRecentItem()
            println("Current State: $state")
            assert(state is BookingsUiState.Error)
            assertEquals(expectedErrorMsg, (state as BookingsUiState.Error).message)
        }
    }
}