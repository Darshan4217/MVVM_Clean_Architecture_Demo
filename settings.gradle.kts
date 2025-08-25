pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AssignmentTask"

// Core modules
include(":core:common")
include(":core:ui")
include(":core:network")

// Feature modules
include(":feature:character:list:data")
include(":feature:character:list:domain")
include(":feature:character:list:presentation")
include(":feature:character:list:di")
include(":feature:character:detail:data")
include(":feature:character:detail:domain")
include(":feature:character:detail:presentation")
include(":feature:character:detail:di")

// App module
include(":app")
 