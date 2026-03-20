# Project Context & Guidelines

## 🎯 Project Overview
- **Name:** ExpenseTest
- **Purpose:** An Android app for tracking and analyzing expenses.
- **Architecture:** Clean Architecture with Feature-based modularization.

## 🏗 Tech Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Dependency Injection:** (Specify here, e.g., Hilt/Koin)
- **Database:** Room
- **Networking:** (Specify here, e.g., Retrofit)
- **Asynchronous Tasks:** Coroutines & Flow

## 🎨 Coding Standards
- Prefer **Material3** components.
- Use **Timber** for logging (if applicable).
- Keep ViewModels clean by delegating business logic to **UseCases**.
- Use **Libs.versions.toml** for all dependency management.

## 🧪 Testing Guidelines
- **Unit Tests:** MockK for mocking, Kotlinx-Coroutines-Test for flows.
- **UI/Screenshot Tests:** Using the Android Compose Screenshot Testing plugin.
- **Baselines:** Always update baseline screenshots after intentional UI changes.

## 📁 Module Structure
- `:app`: Main entry point and navigation.
- `:core`: Shared UI components, themes, and base database logic.
- `:feature-expenses`: Specific features related to expense tracking.

## ⚠️ Known Issues / Constraints
- AGP versions must be compatible with the current IDE build (currently `9.2.0-alpha03`).
- Lint may crash on certain Compose detectors with alpha AGP versions.
