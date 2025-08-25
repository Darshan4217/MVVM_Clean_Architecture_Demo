# Assignment Task - Feature-Based Modularized Android App

A modern Android application built with feature-based modularization, following Clean Architecture principles, MVVM pattern, and SOLID principles.

## 🏗️ Architecture Overview

This project follows a **feature-based modularization** approach with **Clean Architecture** principles:

```
app/
├── core/
│   ├── common/          # Shared utilities, Result wrapper
│   ├── network/         # API interfaces, network configuration, DTOs
│   └── ui/              # Shared UI components, theme
├── feature/
│   └── character/
│       ├── list/        # Character list feature
│       │   ├── data/    # Data models, mappers, repository implementation
│       │   ├── domain/  # Domain models, repository interface, use cases
│       │   ├── presentation/ # UI components, ViewModels
│       │   └── di/      # Hilt dependency injection module
│       └── detail/      # Character detail feature
│           ├── data/    # Data models, mappers, repository implementation
│           ├── domain/  # Domain models, repository interface, use cases
│           ├── presentation/ # UI components, ViewModels
│           └── di/      # Hilt dependency injection module
└── app/                 # Main application module
```

### 🎯 Domain Model Strategy

Each feature maintains its own domain & data models for better separation of concerns:

- **List Feature Character**: Minimal model with essential fields for list display
- **Detail Feature Character**: Complete model with all character information
- **Independent Evolution**: Models can evolve independently based on feature needs

## 🎯 Features

### Core Features
- **Character List Screen**: Displays a list of characters from the Rick and Morty API
- **Character Detail Screen**: Shows detailed information about a specific character
- **Navigation**: Seamless navigation between list and detail screens

### Technical Features
- **MVVM Architecture**: ViewModels with StateFlow for reactive UI updates
- **Clean Architecture**: Separation of concerns with clear layer boundaries
- **Feature-Based Modules**: Each feature is completely self-contained with independent domain models
- **Domain-Specific Models**: Separate Character models for list and detail features
- **Dependency Injection**: Hilt for dependency management
- **Reactive Programming**: Kotlin Coroutines and Flows
- **Modern UI**: Jetpack Compose with Material 3 design
- **Network Layer**: Retrofit with OkHttp for API calls
- **Image Loading**: Coil for efficient image loading
- **Testing**: Unit tests with MockK and Turbine

## 🛠️ Technology Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Async Programming**: Coroutines + Flows
- **Image Loading**: Coil
- **Testing**: JUnit, MockK, Turbine
- **Build System**: Gradle with Version Catalogs

## 📱 Screenshots
<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/1782927c-e601-4d2d-ae1e-96ecec8275af" />

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/030ab900-b2ba-444b-a887-a994b847b5cb" />

### Character List Screen
- Displays characters in a scrollable list
- Shows character image, name, species, and status
- Clickable items for navigation to detail screen

### Character Detail Screen
- Full character information display
- Large character image
- Detailed information (status, species, origin, location, episodes, created, gender)
- Scrollable content

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+
- Kotlin 2.0.21+

### Installation

1. **Clone the repository**

   git clone https://github.com/Darshan4217/MVVM_Clean_Architecture_Demo.git

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder and select it

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project (Build → Make Project)

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the Run button or press Shift + F10

## 🧪 Testing

### Test Coverage
- **Feature Modules**: Comprehensive testing for each feature
  - Data layer: repositories
  - Presentation layer: ViewModels
- **Core Modules**: Shared utilities and components
- **UI Components**: Compose UI testing with Compose Test

## 📊 API Endpoints

The app uses the Rick and Morty API:

- **Character List**: `https://rickandmortyapi.com/api/character`
- **Character Detail**: `https://rickandmortyapi.com/api/character/{id}`

## 🏛️ Architecture Principles

### SOLID Principles
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Subtypes are substitutable for their base types
- **Interface Segregation**: Clients depend only on interfaces they use
- **Dependency Inversion**: High-level modules don't depend on low-level modules

### Domain Model Strategy
- **Feature Independence**: Each feature maintains its own domain models
- **Model Optimization**: Models are tailored to specific feature requirements
- **Evolution Isolation**: Changes to one feature's models don't affect other features
- **Clear Boundaries**: Each feature owns its domain logic completely

### Clean Architecture Layers
1. **Presentation Layer**: UI components and ViewModels (in feature modules)
2. **Domain Layer**: Business logic, entities, and use cases (in feature modules) - **Independent per feature**
3. **Data Layer**: Repository implementations and data sources (in feature modules)
4. **Network Layer**: API interfaces and network configuration (core:network)

### MVVM Pattern
- **Model**: Domain entities and business logic
- **View**: Compose UI components
- **ViewModel**: Manages UI state and business logic

### Feature-Based Modularization
- **Independent Features**: Each feature is self-contained with its own data, domain, and presentation layers
- **Domain-Specific Models**: Each feature maintains its own domain models for better separation of concerns
- **Shared Core**: Common utilities, UI components, and network layer are shared
- **Clear Boundaries**: Features can be developed and tested independently
- **Team Scalability**: Different teams can work on different features
- **Model Independence**: Features can evolve their models without affecting other features

## 🔧 Build Configuration

### Module Dependencies
```
app/
├── core:common         # Shared utilities
├── core:ui            # Shared UI components
├── core:network       # Network layer
├── feature:character:list:presentation    # Character list UI
├── feature:character:list:domain          # Character list business logic
├── feature:character:list:data            # Character list data layer
├── feature:character:list:di              # Character list dependency injection
├── feature:character:detail:presentation  # Character detail UI
├── feature:character:detail:domain        # Character detail business logic
├── feature:character:detail:data          # Character detail data layer
└── feature:character:detail:di            # Character detail dependency injection

feature:character:list:domain/
└── core:common        # Shared utilities

feature:character:list:data/
├── core:common        # Shared utilities
├── core:network       # Network layer
└── feature:character:list:domain  # Domain layer

feature:character:detail:domain/
└── core:common        # Shared utilities

feature:character:detail:data/
├── core:common        # Shared utilities
├── core:network       # Network layer
└── feature:character:detail:domain  # Domain layer

core:ui/
└── core:common        # Shared utilities

core:network/
└── core:common        # Shared utilities
```

### Build Variants
- **Debug**: Development build with logging enabled
- **Release**: Production build with ProGuard optimization

## 📁 Project Structure Details

### Core Modules
- **core:common**: Contains `Result<T>` wrapper for consistent error handling
- **core:network**: Contains API interfaces, DTOs, and network configuration
- **core:ui**: Contains shared UI components, theme, and common UI utilities
 
### Character Models

#### List Feature Character Model
```kotlin
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    val origin: Location,
    val location: Location
)
```

#### Detail Feature Character Model
```kotlin
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
```

**Note**: Both models have the same structure but are completely independent, allowing each feature to evolve its model independently.

### Feature Modules
Each feature module follows the same structure with independent domain models:

#### Character List Feature
- **list:domain**: Contains `Character` model optimized for list display (id, name, status, species, image)
- **list:data**: Data models, mappers, and repository implementation for list feature
- **list:presentation**: UI components and ViewModels for character list
- **list:di**: Dependency injection configuration for list feature

#### Character Detail Feature
- **detail:domain**: Contains complete `Character` model with all fields (origin, location, episodes, etc.)
- **detail:data**: Data models, mappers, and repository implementation for detail feature
- **detail:presentation**: UI components and ViewModels for character detail
- **detail:di**: Dependency injection configuration for detail feature

#### Data Layer
- **Models**: Feature-specific data models for mapping between network and domain
- **Mappers**: Extension functions for converting between different model types
- **Repository Implementation**: Concrete implementation of the repository interface

#### Domain Layer
- **Models**: Pure business logic models, independent of data sources and other features
- **Repository Interface**: Contract defining data operations
- **Use Cases**: Business logic operations that coordinate between repositories

#### Presentation Layer
- **Screens**: Compose UI components
- **ViewModels**: State management and business logic coordination
- **UI State**: Sealed classes representing different UI states

#### Dependency Injection
- **Hilt Modules**: Module definitions for dependency injection

## 📝 Code Style

- **Kotlin Coding Conventions**: Follows official Kotlin style guide
- **Compose Best Practices**: Uses recommended Compose patterns
- **Clean Code**: Meaningful names, small functions, clear structure
- **Extension Functions**: Uses Kotlin extension functions for mapping operations

## 🧪 Testing Strategy

### Unit Tests
- **Repositories**: Test data operations with mocked dependencies
- **ViewModels**: Test UI state management with mocked use cases

### Android Tests
- **UI Components**: Test Compose UI behavior

### Test Dependencies
- **MockK**: Mocking framework for Kotlin
- **Turbine**: Testing library for Flows
- **JUnit**: Unit testing framework
- **Compose Test**: UI testing for Compose

## 🚀 Benefits of This Architecture

### For Developers
- **Clear Structure**: Easy to understand where to add new code
- **Independent Development**: Features can be developed in parallel
- **Easy Testing**: Each layer can be tested independently
- **Code Reusability**: Shared components reduce duplication
- **Domain Model Independence**: Each feature can evolve its models without affecting others

### For Teams
- **Scalability**: Multiple teams can work on different features
- **Code Ownership**: Clear boundaries for code responsibility
- **Reduced Conflicts**: Less merge conflicts due to feature isolation
- **Faster Development**: Parallel development of features
- **Model Evolution**: Teams can modify their feature's domain models independently

### For Business
- **Maintainability**: Easy to maintain and update features
- **Quality**: Comprehensive testing ensures code quality
- **Performance**: Modular compilation improves build times
- **Future-Proof**: Architecture supports future growth
- **Feature Independence**: Features can be modified or replaced without affecting others

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes following the established architecture
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request


## 🙏 Acknowledgments

- Rick and Morty API for providing the data
- Android team for Jetpack Compose
- Google for Hilt dependency injection
- Square for Retrofit networking library

## 📞 Support

For questions or issues, please open an issue in the repository or contact the development team.

## 🔮 Future Enhancements

This architecture supports easy addition of:
- **New Features**: Follow the same pattern for new features with independent domain models
- **Additional Data Sources**: Easy to add local database, caching, etc.
- **Analytics**: Can be added at the presentation layer
- **Multi-Module Apps**: Can be extended to support multiple app modules
- **Model Customization**: Each feature can customize its domain models based on specific requirements
- **API Evolution**: Features can adapt to API changes independently

