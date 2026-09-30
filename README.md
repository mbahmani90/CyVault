<p align="center">
  <img src="cyvault_icon.png" width="96" alt="CyVault icon" />
</p>

<h1 align="center">CyVault</h1>

<p align="center">
  A Kotlin Multiplatform (Android + iOS) wallet app for managing bank cards, with open-banking account linking.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.4-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Compose%20Multiplatform-1.11-4285F4?logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/platforms-Android%20%7C%20iOS-3DDC84" />
  <img src="https://img.shields.io/badge/status-work%20in%20progress-orange" />
</p>

---

## Overview

CyVault lets a user sign in, link a bank account through **open banking** (via the [Yapily](https://www.yapily.com/) API), and store and manage their cards in a vault.

The app is built with **Kotlin Multiplatform** and **Compose Multiplatform**. UI, ViewModels, business logic and networking are shared between Android and iOS. Only a small set of platform-specific pieces use `expect`/`actual` or native bridges.

## Features

- **Authentication** (AWS Cognito via Amplify)
  - Email/password sign-up with email verification code
  - Sign-in, forgot password, sign-out
  - **Sign in with Google** (OAuth, deep-link callback `cyvault://callback`)
- **Add card via open banking** (multi-step flow)
  1. Pick a country and search for your bank (Yapily institutions API)
  2. Authorise access in the bank's page (Chrome Custom Tabs on Android, `SFSafariViewController` on iOS)
  3. Return through a deep link, then choose one of the linked accounts
  4. Enter card details and save
- **Vault**: list and delete saved cards

## Architecture

The project uses **Clean Architecture** with **MVI**, split into feature modules:

```
CyVault
├── androidApp       Android entry point (Application, MainActivity)
├── iosApp           iOS entry point (SwiftUI host + Amplify Swift auth bridge)
├── app              Shared app shell: root Compose App, navigation graph, Koin setup
├── core             Shared infrastructure: Ktor client, platform DI, routes, SessionManager
├── design           Design system: theme, colors, typography, shapes, reusable components
├── authentication   Feature: sign-in / sign-up / Google / password reset
├── vault            Feature: vault home, card list, add-card (open banking)
└── amplify-backend  AWS Amplify Gen 2 backend (Cognito + Google IdP), TypeScript
```

Each feature module follows the same layering:

```
feature/
├── data/           DTOs, remote services (Ktor), mappers, repository implementations
├── domain/         Models, repository interfaces, use cases
├── presentation/   Compose screens + ViewModel (State / Intent / Effect)
└── di/             Koin module
```

### MVI

Every screen has three types:

- **State**: an immutable data class that the screen renders, exposed as `StateFlow`.
- **Intent**: a sealed class of user actions sent to the ViewModel.
- **Effect**: one-off events (snackbar, navigation, opening an external URL), delivered through a `Channel` so each one is consumed **exactly once** and never replayed after recomposition or rotation.

See [`docs/Effect.md`](docs/Effect.md) for why `Channel` is used instead of `SharedFlow`.

### Platform-specific code

| Concern | Shared API | Android | iOS |
|---|---|---|---|
| Authentication | `AuthApiService` (`expect`/`actual`) | Amplify Android | Amplify Swift, through the `IosAuthBridge` callback interface implemented in Swift |
| Open bank authorisation page | `BankAppLauncher` (`expect`/`actual`) | Chrome Custom Tabs | `SFSafariViewController` |
| DI | `platformModule` | Ktor OkHttp engine | Ktor Darwin engine |

## Tech stack

| Area | Library |
|---|---|
| Language | Kotlin 2.4 (Multiplatform) |
| UI | Compose Multiplatform 1.11, Material 3 |
| Navigation | Navigation Compose (KMP), type-safe `@Serializable` routes |
| Async | Kotlin Coroutines & Flow |
| DI | Koin 4 |
| Networking | Ktor 3 (OkHttp / Darwin), kotlinx.serialization |
| Auth | AWS Amplify (Cognito) + Google federated sign-in |
| Open banking | Yapily REST API |
| Backend | AWS Amplify Gen 2 (TypeScript) |

## Getting started

### Requirements

- Android Studio (latest) with the Kotlin Multiplatform plugin
- Xcode (for iOS)
- JDK 17+
- Node.js 18+ and an AWS account (only needed to deploy your own backend)

### 1. Backend (optional: deploy your own)

```bash
cd amplify-backend
npm install
npx ampx sandbox secret set GOOGLE_CLIENT_ID
npx ampx sandbox secret set GOOGLE_CLIENT_SECRET
npx ampx sandbox
```

Copy the generated `amplify_outputs.json` to:

- `androidApp/src/main/res/raw/amplify_outputs.json`
- `iosApp/iosApp/amplify_outputs.json`

### 2. Open banking

Create a Yapily sandbox application and put your credentials in
`vault/src/commonMain/kotlin/com/cypressit/vault/addCard/data/remote/YapilyConfig.kt`.

### 3. Run

- **Android:** `./gradlew :androidApp:assembleDebug`, or use the run configuration in Android Studio
- **iOS:** open `iosApp/` in Xcode and run

## Security notes & roadmap

CyVault is a portfolio / learning project and **not production-ready**. The known gaps and the planned fixes are:

- **Yapily app secret** is currently read on the client for simplicity. In production, all Yapily calls should go through a backend proxy (for example an AWS Lambda) so the secret never ships in the app.
- **Card data**: the demo model holds a card number and CVV. A real app must never store the CVV and should keep only a **tokenised** card reference from a PCI DSS-compliant provider.
- **Card API**: `CardApiService` points to a placeholder endpoint. Planned: an Amplify Data / AppSync backend with owner-based authorization.
- **Local storage**: SQLDelight is set up for offline caching (not yet implemented), with platform keystore/keychain encryption for sensitive data.
- More unit and UI tests for use cases and ViewModels.

## Author

**Majid Bahmani** · [GitHub](https://github.com/mbahmani90)
