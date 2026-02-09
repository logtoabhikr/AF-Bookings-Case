# AF-Booking-Case-Android-Multi-Module-Clean-Architecture-Mvvm-Compose-Kotlin-Hilt-Flow-Coil  

AFBookings - Android Assessment
simulating an airline booking management interface. Built with Kotlin, Jetpack Compose, and Clean Architecture, featuring pixel-perfect UI implementation and robust engineering practices.

### Screenshots

<img width="240" height="400" alt="Screenshot_20260209_192030" src="https://github.com/user-attachments/assets/3a5fd93f-bcb4-4b61-97bc-770525d6dcb9" />
<img width="240" height="400" alt="Screenshot_20260209_192044" src="https://github.com/user-attachments/assets/87dfcc55-bdfe-4311-b567-e5431f174d51" />

### Project Overview
This project demonstrates a production-grade implementation of a booking detail screen and list view, focusing on:

Complex UI/UX: Custom Tab UI/UX, Custom drawn timelines, parallax headers.

Modern Stack: Latest AGP, Kotlin 2.2+, and Material 3.

Architecture: Strict separation of concerns.

### Tech Stack
Language: Kotlin (2.2.0+)

UI Toolkit: Jetpack Compose (Material 3)

Architecture: MVVM + Clean Architecture (Data / Domain / UI)

Dependency Injection: Dagger Hilt

Async: Coroutines & Flow

Image Loading: Coil

Navigation: Jetpack Navigation & Adaptive Navigation Suite

Build System: Gradle 8.+ (Version Catalogs)

### Architecture
The app follows the Clean Architecture principles:

Domain Layer (Pure Kotlin)
Contains UseCases and Repository Interfaces.

Models: Booking, Segment (Sealed Interface for Polymorphic Flights/Transfers).

Zero Android dependencies.

Data Layer
Repository Impl: AssetBookingRepository reads local JSON.

Hilt Module: Provides AssetManager directly to avoid leaking Context.

Parser: Kotlinx Serialization for efficient JSON parsing.

UI Layer
ViewModel: Exposes StateFlow<UiState> (Loading/Success/Error).

Composables: stateless, preview-ready UI components.


#### Developer
Abhishek Kumar
