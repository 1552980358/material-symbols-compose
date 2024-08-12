# MSC: material-symbols-compose

[![](https://jitpack.io/v/1552980358/material-symbols-compose.svg)](https://jitpack.io/#1552980358/material-symbols-compose)

## Install

1. Install KSP
   - Have a look at [KSP quickstart - Add a processor](https://kotlinlang.org/docs/ksp-quickstart.html#add-a-processor)
      ```kotlin
      plugins {
          id("com.google.devtools.ksp") version "<KSP_VERSION>"
          id("org.jetbrains.kotlin.android") version "<KOTLIN_VERSION>"
      }
      ```    

2. Add Jitpack maven repository url
   - At `settings.gradle.kts`
      ```kotlin
      dependencyResolutionManagement {
          repositories {
              // ...
              // Add following line
              maven { url = uri("https://jitpack.io") }
          }
      }
      ```
      
3. Add MSC annotation and KSP dependencies
   - At app module `build.gradle.kts` dependencies block
      ```kotlin
      dependencies {
          // ...
          val materialSymbolsCompose = "<MSC_VERSION>"
          implementation("com.github.1552980358.material-symbols-compose:annotation:$materialSymbolsCompose")
          ksp("com.github.1552980358.material-symbols-compose:ksp:$materialSymbolsCompose")
          // ...
      }
      ```

