## Requirements to run
- Bearer token. This is set in the App. You can paste it into Google Keep (Google Notes) to copy paste
it more easily into your device
- Minimum Android version: 11 (API level 30)

# Project Specifications
## Screens
- **Movie List Screen**
    - Paginated list of movies
    - Each item displays:
    - Movie title
    - Original title
    - Release date
    - Vote average
    - Load more pagination
    - Loading state
- **Movie Detail Screen**
    - Movie backdrop image using: https://image.tmdb.org/t/p/w500{backdrop_path}
    - Movie title
    - Original title
    - Release date
    - Vote average
    - Vote count
    - Movie overview/description
    - Additional movie information (popularity, language, etc.)
    - Back navigation to list

## Evaluation Criteria
- **Mandatory (Must Have)**
    - ✔️ Kotlin implementation with proper language features
    - ✔️ Jetpack Compose UI with Material Design 3
    - ✔️ Clean Architecture with proper layer separation
    - ✔️ MVVM/MVI pattern with StateFlow state management
- **Valorized (Nice to Have)**
Hilt dependency injection with multiple modules
    - ✔️ Repository pattern with proper abstraction
    - ✔️ Use Cases for business logic encapsulation
    - ✔️ Compose Navigation with type-safe routing
    - ✔️ Comprehensive error handling and loading states
    - ✔️ Efficient pagination and data loading
    - ✔️ Responsive and accessible UI design

> [!NOTE]
> No library was indicated for the HTTP Client. So I chose [Ktor](https://ktor.io/docs/client-create-new-application.html). JFYI: Some legacy projects use Hilt with the anottation processor KAPT. The standart is now KSP [which is 2 times faster](https://kotlinlang.org/docs/ksp-overview.html).
>
> No library was indicated for loading Images from the API. There is still [no built-in way to load non-local-app Images](https://developer.android.com/develop/ui/compose/graphics/images/loading#internet-loading). So I chose [Coil](https://coil-kt.github.io/coil/)
> 
> I'm more used to and prefer Koin for Kotlin Injection. It's my first time using Hilt
>
> I used [version catalog](https://developer.android.com/build/migrate-to-catalogs) of the dependencies which is now the standard for organizing dependencies
> 
> All dependencies added are followed by "Added:"
> 
> Kotlin version used: 2.2.0 (set in `gradle/libs.versions.toml`)
> 
> I added a unit test to test the API in an initial phase (was done before starting the UI)

## API docs
- API rate limiting: 50 req/s - https://developer.themoviedb.org/docs/rate-limiting
- https://developer.themoviedb.org/reference/movie-now-playing-list

## Dates
- Challenge submitted to me at 11:27 (thursday) 31/7/2025
- Deadline date: 4/8/2025 (monday)
- Submission date: 

## Final result preview



## Resources used
- https://developer.android.com/training/dependency-injection/hilt-android
- https://kotlinlang.org/docs/ksp-quickstart.html
