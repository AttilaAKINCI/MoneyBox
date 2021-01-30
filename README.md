# MoneyBox

## UI
MoneyBox application consist of 4 fragments and 1 root activity. Root activity holds a container layout in order to
manage fragments which will be controlled by navigation component.

[APK Link (https://drive.google.com/file/d/1DhOmHSM0OYVD6mK2O4B97mNZ0GwVvrAn/view?usp=sharing)](https://drive.google.com/file/d/1DhOmHSM0OYVD6mK2O4B97mNZ0GwVvrAn/view?usp=sharing)

Fragments :
* SplashFragment
* LoginFragment
* ProductListFragment
* ProductDetailFragment

## MVVM Design
Each UI part or application is assumed as independent feature so each feature has view, viewmodel and model(shown as data group) part of architecture.
Necessary abstractions are applied so as to ensure injections and polymorphic usages of clases.

UI, ViewModel, Repository layers are seperated and they communicate each other with event base informers(resource-informer-observer). 

## Data sharing between fragments
There are couple ways to achieve that. In this task example, I have used activity scoped viewmodels in order to 
share data between different(ProductListFragment - ProductDetailFragment) fragments(UIs)

## Error Handling 
MoneyBox application makes network requests in order to acquire some user dependent data. Network related or device related
errors can be occured during runtime. 

For network requests HTTP responses between 200 and 299 indicates that successful communication, between 400 and 599
indicates error which has to be handled.

Reposity layer handles that errors and informs ViewModel Layer with Resource type.
ViewModel Layer is informs UI layer with Informer type if it necessary.

Notes: `Optional json field should be marked as nullable ( ? ) otherwise service response can not be mapped to data class`

## Testing
For unit tests, I created fake version of repositories and some utility classes. Fake versions use same interface 
in order to make easier to test viewmodels.

Context dependent tests are placed under androidTest folder, Independent class and utility tests are placed 
under test folder.

## Request Header Management
For each request, some header parameters has to be provided by application. 

##### Request Manipulation for header management
Retrofit and httpClient is initiated in hilt module so During the initialization additional interceptors are applied
in order to manipulate each request.

Note : `Login service not requires to have Authorization field in header`

Services -->
 * POST /users/login
 * GET /investorproducts
 * POST /oneoffpayments

Headers -->

|  Key | Value |
| ------------- | ------------- |
| AppId  | 3a97b932a9d449c981b595  |
| Content-Type  | application/json  |
| appVersion | 7.15.0 |
| apiVersion | 3.0.0 |
| Authorization  | Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=  |

##### Response Check for authorization bearer token expiration
On every request response, interceptors control response for some error body that tells bearer token expired.
If token expires, application automatically returns login screen and gives that information to user via snackbar message


## Application UI Flow

#### SplashFragment
Application initiates with SplashFragment and owl animation and company logo is shown at the start. 
After some amount of time owl and company logo are moved with fragment transition animation and LoginFragment is initiated.

#### LoginFragment
In Login fragment 3 different input fields (email, password, name) and 1 login button is placed. name field is processed
as optional and not empty validation checks are applied to email and password fields using xml binding adapters.

If there is any validation error, UI automatically updated with information bubble. With the action of login button, 
fields are controlled again and view triggers viewmodel function in order to send server request using databinding features.

owl icon at the bottom is moved to new place at the headerview of ProductListFragment after successful login action.

Any error and incident is shared with user on snackbar

#### ProductListFragment
ProductListFragment is consist of 2 different areas. One of them is headerview and the other one is product list.
Header view gives a welcome message and informs user about total plan values of his/her products.
Welcome message changes depending on the name parameter which is optinally passed from Login Screen. 

In ProductListFragment starts with facebook shimmer effect in order to show user some content loading. After network request 
is completed, prodcuts are listed sequentially.

Each product row contains productName, planValue, moneyBoxValue and product icon if it provided.

Selection of any item, leads user to ProductDetail Screen


#### ProductDetailFragment
ProductDetail Screen gives details about product and also provides some additional functionality to user.
User can add some money to his/her moneybox using button(ADD £10 - amount changes according to QuickAddDepositAmout information)
After service call UI automatically updates itself and also Product ListScreen updates itself too.

User can download some sepecial documents is related about product pressing 'Download Document' button according to permission 
selection response.

## 3rd party lib. usages & Tech Specs
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
* Unit testing samples & HILT integrations for testing


#### Wire Frame
<img src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/wireframe.png" width="200">

#### ScreenShots
<img src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/1.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/2.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/3.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/4.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/5.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/task/images/6.png" width="200">   






