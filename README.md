# Knowledge Base Android + Wear OS App

**Multi-module Android knowledge-base application with a full phone app and a Wear OS companion module, built with Java/XML, SQLite, REST API integration, PHP/MySQL backend services, multilingual support, and wearable-optimized knowledge search handoff.**

Knowledge Base is an Android application for organizing internal company knowledge, problems, categories, users, and solutions. The updated project now separates the original phone application into a dedicated `phone` module and adds a new `wearable` module for Wear OS devices.

The phone module contains the full Knowledge Base experience, while the wearable module provides a watch-optimized entry point and knowledge-search handoff experience for smaller screens.

<img width="1536" height="1024" alt="knowledge_base" src="https://github.com/user-attachments/assets/bc873185-a7ce-477f-b01b-5d82c1616c4f" />

---

## Why This Project Matters

This repository is a strong portfolio project because it shows the evolution of an Android business app into a multi-device Android ecosystem project.

It demonstrates:

- Native Android development
- Multi-module Android project organization
- Full phone application support
- Wear OS companion module support
- Java/XML Android app development
- SQLite local persistence
- REST API communication
- PHP/MySQL backend integration
- Authentication-oriented app flow
- Company/user/category/solution management
- Multilingual app behavior
- Wearable entry-screen design
- Knowledge search handoff from watch form factor
- Modernized Gradle/AGP configuration
- Separate build targets for phone and wearable


---

## Project Overview

The app is designed as a company knowledge-base system. It helps teams document known problems and their solutions so employees can search and reuse existing knowledge instead of repeatedly solving the same issue.

The updated project contains two app modules:

| Module | Purpose |
|---|---|
| `phone` | Existing full Knowledge Base Android application |
| `wearable` | Wear OS application with a watch-optimized entry screen and knowledge search handoff |

The phone app keeps the complete business workflow. The wearable app adds a lightweight watch experience that supports quick entry into the knowledge-search use case.

---

## Main Features

### Full Phone Knowledge Base App

The phone module contains the original Android Knowledge Base application.

It supports business workflows for:

- Login/authentication
- Company knowledge-base access
- Problem and solution lookup
- Solution detail display
- Solution management
- Category management
- User management
- Personal user information
- Multilingual app behavior
- Local SQLite preference/data persistence
- REST API communication with backend services

### Wear OS Companion Module

The wearable module adds a watch-focused experience.

It is designed for:

- Wear OS / watch form factor
- Compact entry screen
- Knowledge search handoff
- Lightweight interaction from the wrist
- Separate wearable package and build target

This gives the project a stronger product story because the same knowledge-base concept can now be accessed from both a phone and a wearable device.

### Local SQLite Storage

The phone app uses SQLite for local persistence. This supports stored app state and language preference behavior so users do not have to repeatedly select the same preference.

### REST API and Backend Integration

The app integrates with REST/microservice endpoints backed by PHP/MySQL services. This demonstrates practical mobile/backend communication and full-stack mobile product development.

### Multilingual Support

The original app supports language selection for:

- English
- Spanish
- French
- Portuguese

The selected language is stored locally so the app can remember the user's preference.

### Administrative Workflows

The phone app includes administrative screens for managing:

- Users
- Categories
- Solutions
- Solution details
- User information

This makes the app closer to a real internal business tool than a simple sample app.

---

## Technical Stack

| Area | Technology |
|---|---|
| Platform | Android, Wear OS |
| Project type | Multi-module Android project |
| Modules | `phone`, `wearable` |
| Phone language | Java |
| Phone UI | XML layouts |
| Phone local storage | SQLite |
| Backend integration | REST / microservices |
| Backend stack | PHP, MySQL, Apache |
| Phone data support | GSON, OpenCSV |
| Wearable dependency | AndroidX Wear |
| Build system | Gradle |
| Android Gradle Plugin | 9.2.1 |
| Phone compile SDK | 32 |
| Phone target SDK | 32 |
| Phone min SDK | 26 |
| Wearable compile SDK | 36 |
| Wearable target SDK | 36 |
| Wearable min SDK | 26 |
| Phone Java compatibility | Java 17 |
| Wearable Java compatibility | Java 21 |
| License | GPL-3.0 |

---

## Repository Structure

```text
Knowledge-Base__Android_App/
├── phone/
│   ├── src/
│   ├── build.gradle
│   └── proguard-rules.pro
├── wearable/
│   ├── src/
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
├── resources/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── LICENSE
```

The updated `settings.gradle` includes:

```gradle
include ':phone', ':wearable'
```

---

## Module Details

### `phone`

The `phone` module contains the full Android Knowledge Base application.

It uses:

- Application ID: `com.prpinfo.bancodesolucoes`
- Namespace: `com.prpinfo.bancodesolucoes`
- Compile SDK: 32
- Minimum SDK: 26
- Target SDK: 32
- Version code: 12
- Version name: 12.0
- Java 17 source/target compatibility

Important dependencies include:

- AppCompat
- ConstraintLayout
- Legacy support v4
- Material Components
- VectorDrawable
- GSON
- OpenCSV
- JUnit
- AndroidX test libraries
- Espresso

### `wearable`

The `wearable` module contains the Wear OS application.

It uses:

- Application ID: `com.prpinfo.bancodesolucoes.wearable`
- Namespace: `com.prpinfo.bancodesolucoes.wearable`
- Compile SDK: 36
- Minimum SDK: 26
- Target SDK: 36
- Version code: 2
- Version name: 2.0
- Java 21 source/target compatibility

Important dependencies include:

- AndroidX Wear

---

## Architecture Direction

The updated project follows a multi-module direction:

```text
Root project
      ├── phone
      │     └── Full Knowledge Base Android app
      │
      └── wearable
            └── Wear OS entry/search handoff app
```

This structure is better than keeping all functionality inside one app module because phone and wearable experiences have different UI, device, and interaction constraints.

---

## Phone App Workflow

The phone app provides the complete knowledge-base workflow:

```text
User signs in
      ↓
User selects or reuses language preference
      ↓
User searches the knowledge base
      ↓
User views problem/solution records
      ↓
Administrators manage users, categories, and solutions
      ↓
Data is persisted locally and synchronized through backend services
```

This workflow demonstrates a real business application with authentication, search, persistence, backend communication, and administrative functionality.

---

## Wearable App Workflow

The wearable module supports a lightweight watch-oriented flow:

```text
User opens Knowledge Base on watch
      ↓
Watch-optimized entry screen appears
      ↓
User starts the knowledge search handoff
      ↓
Phone/full experience can handle the richer knowledge-base workflow
```

This is useful for quick access from a wearable while keeping the full management experience on the phone.

---

## Build Commands

Build the phone app:

```bash
./gradlew :phone:assembleDebug
```

Build the wearable app:

```bash
./gradlew :wearable:assembleDebug
```

Build both modules:

```bash
./gradlew assembleDebug
```

---

## Skills Demonstrated

This repository demonstrates several Android and mobile engineering skills:

- Native Android app development
- Multi-module Android project setup
- Java Android programming
- XML UI development
- Wear OS companion app support
- AndroidX Wear usage
- SQLite local persistence
- REST API integration
- PHP/MySQL backend integration
- Authentication-oriented workflow
- User, category, and solution management
- Multilingual app behavior
- Local preference persistence
- App packaging and versioning
- Gradle/AGP modernization
- Separate phone and wearable build targets
- Business-product mobile architecture

---

## Owner

by Harold Paulino

---

## License

This project is licensed under the GPL-3.0 license.
