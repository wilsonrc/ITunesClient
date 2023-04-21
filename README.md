 # ITunes Android Client 
This application shows the search results of the Itunes API using MAD (Modern Android Development) skills.
### Table of Contents
 - Introduction
 - Architecture
 - Getting Started
 - Libraries Used

## Introduction
This project is a sample Android application built using Kotlin, implementing the MVVM (Model-View-ViewModel) architectural pattern. The app fetches data from a remote server and stores it locally using the Room database. The app uses Retrofit for network calls, Hilt for dependency injection, and Room for local data persistence.

## App Preview
![Home Screen](https://user-images.githubusercontent.com/11259931/233521428-e788d9c1-4c9a-494d-ba26-31aa31a2ea0a.PNG)
![Home Screen Dialog](https://user-images.githubusercontent.com/11259931/233521445-c44e1619-1de3-49ae-939c-de4f12aa6d3f.PNG)

### Architecture
![image](https://user-images.githubusercontent.com/11259931/230985378-b18c3140-ee91-4778-a152-a3e45336d7d2.png)


The app follows the MVVM architectural pattern with UI and Data layers. The main components of the app are:
UI layer: Responsible for rendering the UI and user interactions.
Data layer: Responsible for handling the data flow between the UI and the remote server or local database.
In addition, the app uses Repositories to handle data operations, providing a single source of truth for the application.


### Getting Started
- To run the app, follow these steps:
- Clone this repository to your local machine.
- Open the project using Android Studio.
- Connect an Android device or use an emulator.
- Build and run the app.
Language and Libraries Used

## Kotlin
Kotlin is a modern, expressive, and safe programming language fully interoperable with Java. It is used as the primary language for this project.
## Ktor
Ktor is an open-source, asynchronous framework for building web applications, APIs, and microservices in Kotlin. Developed by JetBrains, it allows developers to create server and client-side applications in a concise and expressive manner, leveraging Kotlin's language features and built-in coroutines for efficient asynchronous programming.
## Hilt
The hilt is a dependency injection library for Android built on top of Dagger. It simplifies the process of providing dependencies to various components of the 
## Mockk
Mockk is a Kotlin-native mocking library designed specifically for Kotlin projects. It provides a powerful and flexible DSL (Domain-Specific Language) for creating, verifying, and configuring mock objects in tests. Mockk allows developers to easily create and manage mocks, stubs, and spies, making it a popular choice for unit testing and test-driven development in Kotlin-based applications.

## Best Practices and Considerations
While developing this project, we considered the following best practices and guidelines to ensure high-quality, maintainable, and scalable code:

- Code organization: Followed the recommended package structure for organizing code, separating components by feature and responsibility.
Performance: Used efficient data structures and algorithms to ensure optimal performance. Utilized Kotlin coroutines for efficient and non-blocking network and database operations.
- Scalability: Designed the app with future enhancements in mind, adhering to the SOLID principles to allow easy addition and modification of features.
- Error handling: Implemented proper error handling for network calls. Provided appropriate error messages to users and logged errors for debugging purposes.
- Testing: Wrote unit tests for the ViewMode to ensure correct behavior.
