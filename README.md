# Knowledge Base Android App

**Native Android knowledge-base application built with Java, XML layouts, SQLite local storage, REST API integration, and a PHP/MySQL backend for managing companies, users, categories, problems, and solutions.**

This project is a full Android application for organizing internal company knowledge. It allows administrators to create and manage company knowledge-base records while authorized users can search, view, and work with stored problem/solution content.

The app demonstrates end-to-end mobile application development: Android UI, local persistence, network communication, backend service integration, multilingual user experience, authentication flow, and administrative data-management screens.

---

## Why This Project Matters

This repository is a strong portfolio project because it shows more than a simple Android screen or sample app. It demonstrates the ability to design and build a complete mobile product that connects local Android storage, remote services, and user-facing business workflows.

The project highlights:

- Native Android development
- Java-based Android application architecture
- XML layout implementation
- SQLite local database persistence
- REST API consumption
- PHP/MySQL backend integration
- Apache-hosted microservice communication
- Authentication and login flow
- User, category, and solution management
- Multilingual app behavior
- Search and lookup workflows
- App release/version management
- Play Store-oriented application packaging

This is useful for employers because it shows practical product development: not just isolated code, but a working app with data, users, screens, backend communication, and release structure.

---

## Project Overview

The app is designed as a company knowledge-base system. It helps teams document known problems and their solutions so employees can search and reuse existing knowledge instead of solving the same issue repeatedly.

The main concept is:

1. A company is created.
2. Administrators manage users, categories, problems, and solutions.
3. Authorized users search the knowledge base.
4. Users view stored solutions and related details.
5. App preferences, such as language selection, are stored locally.

---

## Main Features

### Company Knowledge Base

The app allows users to organize problem/solution content around a company structure. This makes the app suitable for internal support teams, technical documentation, operational troubleshooting, and reusable team knowledge.

### Problem and Solution Management

The project includes dedicated screens and classes for managing solutions, solution details, categories, and lookup results.

Relevant application areas include:

- Solution lookup
- Solution detail display
- Solution management
- Category management
- User management
- Personal user information

### Search and Lookup Workflow

The app includes lookup activities and list adapters that support searching and displaying knowledge-base records. This is central to the product value: helping users find an existing solution quickly.

### Local SQLite Storage

The app stores data locally using SQLite. This supports persistent app state and local data access without requiring every user preference or app state change to be fetched from the server.

The current README notes that language preference is stored in SQLite so the user does not have to select it again unless they choose a different language.

### REST API and Backend Integration

The app consumes REST APIs and microservices backed by PHP/MySQL on an Apache HTTP server. This demonstrates real mobile-to-backend integration and a full-stack understanding of how Android apps communicate with server-side systems.

### Multilingual Support

The app supports language selection for:

- English
- Spanish
- French
- Portuguese

The selected language is stored locally so the app can remember the user's preference.

### Authentication Flow

The application launches through a login activity and includes multiple authenticated management screens. This shows experience with user-oriented app flows and role-based product behavior.

### Administrative Screens

The project includes management screens for:

- Users
- Categories
- Solutions
- Solution details
- User information

This makes the app closer to a real business application than a one-screen demo.

---

## Technical Stack

| Layer | Technology |
|---|---|
| Mobile platform | Android |
| Primary language | Java |
| Additional language | Kotlin present in project setup |
| UI | XML layouts |
| Local database | SQLite |
| Backend API | REST / microservices |
| Backend stack | PHP, MySQL, Apache |
| Data format support | JSON / GSON |
| CSV support | OpenCSV |
| Android libraries | AppCompat, ConstraintLayout, Material Components |
| Minimum SDK | 26 |
| Target SDK | 32 |
| Version | 12.0 |

---

## Android Architecture Notes

This project uses a traditional native Android structure with activities, adapters, utility classes, and a SQLite helper/core layer.

Important source areas include:

```text
app/src/main/java/com/prpinfo/bancodesolucoes/
├── LoginActivity.java
├── MainActivityJava.java
├── LookupActivity.java
├── LookupAbstractActivity.java
├── LookupActivityListAdapter.java
├── LookupSolutionDetailAlert.java
├── ManageSolutionsActivity.java
├── ManageSolutionsDetailActivity.java
├── ManageSolutionsListActivity.java
├── ManageCategoriesActivity.java
├── ManageCategoriesDetailActivity.java
├── ManageUsersActivity.java
├── MyInfoActivity.java
├── SqliteCore.java
├── HttpClient.java
├── AESUtils.java
├── Languages.java
├── Utilities.java
└── Solution.java
```

---

## Key Components

### `LoginActivity`

Handles the launch/login flow for the application.

### `MainActivityJava`

Provides the main application entry point after login and routes users into the knowledge-base workflows.

### `LookupActivity` and `LookupAbstractActivity`

Support searching and viewing knowledge-base content.

### `ManageSolutionsActivity`

Supports solution administration and solution-related workflows.

### `ManageCategoriesActivity`

Supports organization of knowledge-base records by category.

### `ManageUsersActivity`

Supports user-management workflows for the application.

### `SqliteCore`

Provides the local SQLite persistence layer.

### `HttpClient`

Handles backend communication with REST/microservice endpoints.

### `Languages`

Supports language selection and multilingual app behavior.

### `AESUtils`

Provides encryption-related utility functionality.

---

## Android Manifest Highlights

The app declares permissions for:

- Internet access
- WiFi state access
- Network state access
- External storage read access

The app includes multiple activities for login, main navigation, lookup, solution management, category management, user management, and user profile information.

---

## User Experience

The app provides a practical business workflow:

1. The user launches the app.
2. The user signs in.
3. The user selects or uses a previously stored language preference.
4. The user searches the knowledge base.
5. The user views matching problems and solutions.
6. Administrators can manage users, categories, and solution records.

The original project also includes custom image assets created for the app.

---

## Repository Structure

```text
Android_App__Knowledge_Base/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/prpinfo/bancodesolucoes/
│   │       ├── res/
│   │       ├── assets/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── gradle/wrapper/
├── resources/
├── build.gradle
├── settings.gradle
├── README.md
└── LICENSE
```

---

## Build Configuration

The Android module uses:

- `compileSdkVersion 32`
- `minSdkVersion 26`
- `targetSdkVersion 32`
- `versionCode 12`
- `versionName 12.0`
- AppCompat
- ConstraintLayout
- Material Components
- GSON
- OpenCSV

---

## Skills Demonstrated

This repository demonstrates several software engineering and Android development skills:

- Native Android app development
- Java Android programming
- XML UI development
- Activity-based app navigation
- List adapters and custom list rendering
- SQLite database design and persistence
- REST API integration
- Backend communication from Android
- PHP/MySQL service integration
- Authentication-oriented app flow
- Multilingual app support
- Local preference persistence
- User-management workflows
- Admin/content-management workflows
- App packaging and release management
- Full-stack mobile product thinking

---

## License

This project is licensed under the GPL-3.0 license.

![Screenshot_20210805-125123](https://user-images.githubusercontent.com/28379115/183942083-72e06a54-0f5a-4a52-a839-9397ba310dd3.png)

![Screenshot_20210805-125140](https://user-images.githubusercontent.com/28379115/183942080-7312b3ee-18d6-4ed7-8d10-810a68d5cbdb.png)

![Screenshot_20210805-130723](https://user-images.githubusercontent.com/28379115/183942052-45ddbb86-2d2a-45d2-9fbd-7b497d163d87.png)

![Screenshot_20210805-130704](https://user-images.githubusercontent.com/28379115/183942057-5e72d22a-348f-4ad1-9e7c-22fafd0ff74b.png)

![Screenshot_20210805-130657](https://user-images.githubusercontent.com/28379115/183942061-44a51b76-3d74-4937-a5e7-ee31b34b1ab5.png)

![Screenshot_20210805-130508](https://user-images.githubusercontent.com/28379115/183942066-3610b120-18a4-4c1f-b019-d4b0e480fa43.png)

![Screenshot_20210805-130453](https://user-images.githubusercontent.com/28379115/183942070-ab1e8506-cbe4-4e50-b358-5bbc7d49a963.png)

![Screenshot_20210805-130438](https://user-images.githubusercontent.com/28379115/183942078-82946780-61fb-4c5e-bd6f-c92365d869b5.png)

![Screenshot_20210805-130742](https://user-images.githubusercontent.com/28379115/183942091-359b7c13-2af6-40ec-aa4a-20c6fb5b5a79.png)

![Screenshot_20210805-130734](https://user-images.githubusercontent.com/28379115/183942044-0a47fe76-041b-4c60-8203-d48a5cd5581d.png)
