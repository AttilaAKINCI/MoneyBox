# MoneyBox

Notes:
* Optional json field should be marked as nullable ( ? ) otherwise service response can not be mapped to data class

* Error handling 200-299 OK ; 400-599 error handling.

* repository and other util based classes injected as a interface so as to ensure abstraction



LoginFragment:
* validation on edittexts for empty check using binding adapters.


3rd party lib. usages & Tech Specs :
* Patterns
    - MVVM design pattern
    - Repository pattern for data management
* JetPack Libs
    - Navigation Component
* Retrofit
* Kotlin Coroutines
* LiveData
* Facebook Shimmer Lib.
* Lottie animation Lib.
* Moshi Json handler
* Timber Client logging
* Dependency Injection (HILT) 
* DataBinding
* Thruth (assertions)
* RecyclerView with List Adapter and DiffUtil
* Transition animation between fragments
* Single Activity multiple Fragments approach




