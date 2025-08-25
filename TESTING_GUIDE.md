# 🧪 **Comprehensive Testing Guide**

This document provides a complete overview of all test cases implemented for the AssignmentTask Android application, covering both **Unit Tests** and **UI Tests**.

## 📋 **Test Coverage Overview**

### **Unit Tests (JVM Tests)**
- ✅ **ViewModels** - Business logic and state management
- ✅ **Use Cases** - Business rules and data flow
- ✅ **Repository** - Data layer implementation
- ✅ **Domain Models** - Data class behavior and validation
- ✅ **Common Utilities** - Result wrapper and extension functions

### **UI Tests (Instrumented Tests)**
- ✅ **Compose Components** - Individual UI components
- ✅ **Screens** - Complete screen behavior and interactions
- ✅ **MainActivity** - App lifecycle and navigation
- ✅ **Network Module** - Dependency injection verification

## 🚀 **Running Tests**

### **Run All Unit Tests**
```bash
./gradlew test
```

### **Run All UI Tests**
```bash
./gradlew connectedAndroidTest
```

### **Run Tests for Specific Module**
```bash
# Core modules
./gradlew :core:common:test
./gradlew :core:domain:test
./gradlew :core:data:test
./gradlew :core:network:test
./gradlew :core:ui:test

# Feature modules
./gradlew :feature:character:list:test
./gradlew :feature:character:detail:test

# App module
./gradlew :app:test
```

### **Run Tests with Coverage**
```bash
./gradlew testDebugUnitTestCoverage
```

## 📱 **Unit Tests (JVM)**

### **1. ViewModel Tests**

#### **CharacterListViewModelTest**
- ✅ Initial state should be Loading
- ✅ Should emit Success state when characters are loaded successfully
- ✅ Should emit Error state when characters loading fails

#### **CharacterDetailViewModelTest**
- ✅ Initial state should be Loading
- ✅ Should emit Success state when character is loaded successfully
- ✅ Should emit Error state when character loading fails
- ✅ Retry should reload character
- ✅ setCharacterId should trigger character loading

### **2. Use Case Tests**

#### **GetCharactersUseCaseTest**
- ✅ Should return characters from repository
- ✅ Should call repository getCharacters method

#### **GetCharacterByIdUseCaseTest**
- ✅ Should return character by id from repository
- ✅ Should call repository getCharacterById method with correct id
- ✅ Should handle different character ids correctly

### **3. Repository Tests**

#### **CharacterRepositoryImplTest**
- ✅ getCharacters should return Success with character list
- ✅ getCharacterById should return Success with character
- ✅ getCharacters should handle API errors
- ✅ getCharacterById should handle API errors
- ✅ refreshCharacters should call API
- ✅ refreshCharacter should call API

### **4. Domain Model Tests**

#### **CharacterTest**
- ✅ Character should have correct properties
- ✅ Character should be equal when properties are same
- ✅ Character should not be equal when properties are different
- ✅ Character copy should create new instance with updated properties
- ✅ Character toString should contain all properties

### **5. Common Utility Tests**

#### **ResultTest**
- ✅ Result Success should contain data
- ✅ Result Error should contain exception
- ✅ Result Loading should be singleton
- ✅ onSuccess should be called for Success state
- ✅ onSuccess should not be called for non-Success states
- ✅ onError should be called for Error state
- ✅ onError should not be called for non-Error states
- ✅ onLoading should be called for Loading state
- ✅ onLoading should not be called for non-Loading states
- ✅ isSuccess should return true for Success state
- ✅ isError should return true for Error state
- ✅ isLoading should return true for Loading state
- ✅ getOrNull should return data for Success state
- ✅ getOrNull should return null for non-Success states
- ✅ exceptionOrNull should return exception for Error state
- ✅ exceptionOrNull should return null for non-Error states

## 🎨 **UI Tests (Instrumented)**

### **1. Component Tests**

#### **LoadingComponentTest**
- ✅ LoadingComponent should display CircularProgressIndicator
- ✅ LoadingComponent should fill max size

#### **ErrorComponentTest**
- ✅ ErrorComponent should display error message
- ✅ ErrorComponent should display retry button
- ✅ ErrorComponent should call onRetry when retry button is clicked
- ✅ ErrorComponent should display both message and button

### **2. Screen Tests**

#### **CharacterListScreenTest**
- ✅ Should display characters when in success state
- ✅ Should display error state when in error state
- ✅ Should call onCharacterClick when character is clicked
- ✅ Should display character details correctly

#### **CharacterDetailScreenTest**
- ✅ Should display character details when in success state
- ✅ Should display error state when in error state
- ✅ Should call retry when retry button is clicked
- ✅ Should display all character information sections
- ✅ Should display character image

### **3. Activity Tests**

#### **MainActivityTest**
- ✅ Should display character list screen initially
- ✅ Should have proper title
- ✅ Should handle configuration changes
- ✅ Should have proper theme

### **4. Network Module Tests**

#### **NetworkModuleTest**
- ✅ Should provide OkHttpClient
- ✅ Should provide Retrofit
- ✅ Should provide CharacterApi
- ✅ Retrofit should have correct base URL
- ✅ OkHttpClient should have interceptors configured

## 🧩 **Test Architecture**

### **Test Dependencies**
```kotlin
// Unit Test Dependencies
testImplementation(libs.junit)
testImplementation(libs.mockk)
testImplementation(libs.turbine)
testImplementation(libs.kotlinx.coroutines.test)

// UI Test Dependencies
androidTestImplementation(libs.androidx.test.ext.junit)
androidTestImplementation(libs.androidx.test.espresso.core)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.compose.ui.test)
androidTestImplementation(libs.androidx.compose.ui.test.manifest)
```

### **Testing Patterns Used**

#### **1. Given-When-Then Pattern**
```kotlin
@Test
fun `test description`() = runTest {
    // Given
    val input = "test data"
    
    // When
    val result = function(input)
    
    // Then
    assertEquals(expected, result)
}
```

#### **2. Fake ViewModels for UI Testing**
```kotlin
class FakeCharacterListViewModel : CharacterListViewModel(mockk()) {
    private var _uiState = CharacterListUiState.Loading
    
    fun setUiState(state: CharacterListUiState) {
        _uiState = state
    }
    
    override val uiState: StateFlow<CharacterListUiState>
        get() = MutableStateFlow(_uiState).asStateFlow()
}
```

#### **3. MockK for Mocking**
```kotlin
@Before
fun setup() {
    repository = mockk()
    useCase = GetCharactersUseCase(repository)
}

@Test
fun `should return data`() = runTest {
    coEvery { repository.getData() } returns flowOf(expectedData)
    // ... test implementation
}
```

#### **4. Turbine for Flow Testing**
```kotlin
@Test
fun `should emit states`() = runTest {
    viewModel.uiState.test {
        val loadingState = awaitItem()
        assertTrue(loadingState is LoadingState)
        
        val successState = awaitItem()
        assertTrue(successState is SuccessState)
        
        awaitComplete()
    }
}
```

## 🔧 **Test Configuration**

### **Hilt Test Configuration**
```kotlin
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NetworkModuleTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    
    @Inject
    lateinit var dependency: Dependency
    
    @Before
    fun setup() {
        hiltRule.inject()
    }
}
```

### **Compose Test Configuration**
```kotlin
@get:Rule
val composeTestRule = createComposeRule()

@Test
fun `test compose component`() {
    composeTestRule.setContent {
        Component()
    }
    
    composeTestRule.onNodeWithText("Text").assertIsDisplayed()
}
```

## 📊 **Test Metrics**

### **Coverage Goals**
- **Unit Tests**: 90%+ line coverage
- **UI Tests**: 80%+ component coverage
- **Integration Tests**: 70%+ flow coverage

### **Test Categories**
- **Fast Tests** (< 1s): Unit tests, model tests
- **Medium Tests** (1-10s): Repository tests, use case tests
- **Slow Tests** (10s+): UI tests, integration tests

## 🚨 **Common Test Issues & Solutions**

### **1. Coroutine Testing**
```kotlin
// Use StandardTestDispatcher for predictable testing
private val testDispatcher = StandardTestDispatcher()

@Before
fun setup() {
    Dispatchers.setMain(testDispatcher)
}

@After
fun tearDown() {
    Dispatchers.resetMain()
}
```

### **2. Flow Testing**
```kotlin
// Use Turbine for flow testing
@Test
fun `test flow`() = runTest {
    flow.test {
        assertEquals(expected1, awaitItem())
        assertEquals(expected2, awaitItem())
        awaitComplete()
    }
}
```

### **3. Compose Testing**
```kotlin
// Use createComposeRule for UI testing
@get:Rule
val composeTestRule = createComposeRule()

@Test
fun `test compose`() {
    composeTestRule.setContent {
        Component()
    }
    
    composeTestRule.onNodeWithText("Text").assertIsDisplayed()
}
```

## 📈 **Continuous Integration**

### **GitHub Actions Example**
```yaml
- name: Run Unit Tests
  run: ./gradlew test

- name: Run UI Tests
  run: ./gradlew connectedAndroidTest

- name: Generate Coverage Report
  run: ./gradlew testDebugUnitTestCoverage
```

## 🎯 **Best Practices**

1. **Test Naming**: Use descriptive test names with `backticks`
2. **Test Structure**: Follow Given-When-Then pattern
3. **Mocking**: Use MockK for dependency mocking
4. **Flow Testing**: Use Turbine for reactive stream testing
5. **UI Testing**: Use Compose Test for UI component testing
6. **Test Isolation**: Each test should be independent
7. **Test Data**: Use factory methods for test data creation
8. **Assertions**: Use specific assertions with clear messages

## 🔍 **Debugging Tests**

### **Enable Test Logging**
```bash
./gradlew test --info
./gradlew connectedAndroidTest --info
```

### **Run Single Test**
```bash
./gradlew test --tests "CharacterListViewModelTest.should emit Success state when characters are loaded successfully"
```

### **Test Reports**
Test reports are generated in:
- `build/reports/tests/` - Unit test reports
- `build/reports/androidTests/` - UI test reports
- `build/reports/jacoco/` - Coverage reports

---

## 📝 **Summary**

This testing suite provides comprehensive coverage of:
- **Business Logic** (ViewModels, Use Cases)
- **Data Layer** (Repository, API)
- **Domain Models** (Data classes, utilities)
- **UI Components** (Compose components, screens)
- **Dependency Injection** (Hilt modules)

The tests follow Android testing best practices and provide a solid foundation for maintaining code quality and preventing regressions.




