### NYTimes Browser

## Project
Simple list/grid of the articles from [NY Times search API](https://developer.nytimes.com/docs/articlesearch-product/1/overview).

Use DDD for data modeling. Use TDD for the implementation.    

## Setup

# Infra
* Latest Gradle 6.0.1
* Latest AGP 3.6.0-rc01

# Gradle
* `dependencies.gradle` - one file to keep all dependencies
* `tests.gradle` - common gradle setup for modules that want to run tests
* `library.gradle` - common gradle setup for kotlin library modules
* `android-library.gradle` - common gradle setup for android library modules

Run `./gradlew assembleD` to have app built. 
Run `./gradlew test testDebug` to execute all tests.

# Modules
* `common-data` - classes used almost in the every module
* `common-article` - library to handle classes and functionality around articles
* `common-article-search` - network library to search article 
* `common-navigation` - interfaces used in feature modules to navigate between
* `lib-retrofit` - network library to setup general code fr networking
* `lib-flex` - library for the flex recycler view that can handle different elements 
* `feature-search` - library that contains UI and model to do articles search
* `feature-article-detail` - library that contains UI to show article detail
* `app` - main app that consumes feature modules

# Dependencies
* Use java8 source and target level
* Use latest stable kotlin
* Use latest RxJava for async operations and data transformations
* Use latest moshi for json parsing with generate json adapters
* Use latest retrofit for loading data from internet
* Use picasso for loading images
* Use junit5 for tests
* Use mockito for mocking things in the tests

# Known issues/Improvements (no order or priority)
* No tests for interceptor (too much mocking for the late night)
* No test for retrofit setup (I probably can but should it be unit test)
* DDD `x.value.value` in the code forces to think about better api for these classes
* DI is just static variables - can be something mature with external library
* Model for the article detail feature
* More unite tests for model, listeners, etc
* At least high level UI tests (this might require better DI) 
