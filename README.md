# MoneyBox

## UI
MoneyBox application consist of 4 fragments and 1 root activity. Root activity holds a container layout in order to
manage fragments which will be controlled by navigation component.

[APK Link (https://drive.google.com/file/d/16TcQnntYwqNocY8Gj_bhWM7iqON-cQR1/view?usp=sharing)](https://drive.google.com/file/d/16TcQnntYwqNocY8Gj_bhWM7iqON-cQR1/view?usp=sharing)

Fragments :
* SplashFragment
* LoginFragment
* ProductListFragment
* ProductDetailFragment

## App Video 

          Normal Run              Login Validations             Login Token Expire         Download Permission

<img src="https://user-images.githubusercontent.com/21987335/112744968-6154e580-8fad-11eb-8356-c2368f339173.gif" width="200"/>  <img 
src="https://user-images.githubusercontent.com/21987335/112745020-c4df1300-8fad-11eb-845f-40f2fe298f42.gif" width="200"/> <img 
src="https://user-images.githubusercontent.com/21987335/112745043-fb1c9280-8fad-11eb-8659-943f2c13041e.gif" width="200"/> <img 
src="https://user-images.githubusercontent.com/21987335/112745046-fd7eec80-8fad-11eb-90a3-29575d09515b.gif" width="200"/>

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
* Permissions
* Glide Image Loading
* Thruth (assertions)
* MockK, JUnit5 for unit Testing
* JUnit4 for integration tests 
* RecyclerView with List Adapter and DiffUtil
* Transition animation between fragments
* Single Activity multiple Fragments approach

## MVVM Design
Each UI part or application is assumed as independent feature so each feature has view, viewmodel and model(shown as data group) part of architecture.
Necessary abstractions are applied so as to ensure injections and polymorphic usages of clases.

UI, ViewModel, Repository layers are seperated and they communicate each other with event base informers(Resource). 

## Data sharing between fragments
There are couple ways to achieve that. In this task example, I have used activity scoped viewmodels in order to 
share data between different(ProductListFragment - ProductDetailFragment) fragments(UIs)

## Error Handling 
MoneyBox application makes network requests in order to acquire some user dependent data. Network related or device related
errors can be occured during runtime. 

For network requests HTTP responses between 200 and 299 indicates that successful communication, between 400 and 599
indicates error which has to be handled. (BaseRepositoryImpl.kt)

Reposity layer handles that errors and informs ViewModel Layer with Resource type.
ViewModel Layer is informs UI layer using LiveData objects if it necessary.

Notes: `Optional json field should be marked as nullable ( ? ) otherwise service response can not be mapped to data class`

## Testing
For unit tests, I have used MockK library with JUnit5 for Unit testing and JUnit4 for integration tests

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
as optional and not empty validation checks are applied to email and password fields.

If there is any validation error, UI automatically updated with information bubble. With the action of login button, 
view triggers viewmodel function in order to send server request using databinding features.

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

#### Wire Frame
<img src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/wireframe.png" width="600">

#### ScreenShots
<img src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/1.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/2.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/3.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/4.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/5.png" width="200">   <img
src="https://github.com/AttilaAKINCI/MoneyBox/blob/main/images/6.png" width="200">   

# License

The code is licensed as:

```
Copyright 2021 Attila Akıncı

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




