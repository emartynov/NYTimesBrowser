### NYTimes Browser

## Project
Simple list/grid of the articles from [NY Times search API](https://developer.nytimes.com/docs/articlesearch-product/1/overview).

Use DDD for data modeling.

Model:
NonEmptyString - string that is not empty
Article - NY Times article
ID - some id in the system
Url - path for some remote resource

## Setup

# Modules
* `common-article` - common library to handle classes and functionality around articles
* `common-article-search` - network library to search article 
* `lib-retrofit` - network library to setup general code fr networking 

# Dependencies
* Use java8 source and target level
* Use latest stable kotlin 1.3.61
* Use latest RxJava for async operations and data transformations
* Use latest moshi for json parsing
* Use latest retrofit for loading data from internet
* Use junit5 for tests
* Use mockito for mocking things in the tests

# Infra
* Latest Gradle 6.0.1
* Latest AGP 3.6.0-rc01

# Known issues
* Domain modules have copy constructor that allows private data change
* No tests for interceptor (too much mocking for the late night)
* No test for retrofit setup (I probably can but should it be unit test)

# Unfinished
* No pagination
* No search
* No detail
* No share 
