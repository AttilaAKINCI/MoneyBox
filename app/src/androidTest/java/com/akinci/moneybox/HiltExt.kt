package com.akinci.moneybox

/** We can access class information of passed T Generic type
 * with the help of reified keyword
 *
 * This Extension is used for initiate a fragment for testing purposes.
**/

/***
 * androidx.fragment:fragment-testing:1.3.0-alpha08 library is used for fragment testing
 * but it is directly effecting to
 * regular navigation component and activity flows.
 * because of that it leaved disabled in build gradle file so I comment out this file content
 *
 * **/

//@ExperimentalCoroutinesApi
//inline fun <reified T : Fragment> launchFragmentInHiltContainer(
//    fragmentArgs: Bundle? = null,
//    themeRedId:Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
//    fragmentFactory : FragmentFactory? = null,
//    crossinline action: T.() -> Unit = {}
//){
//    val mainActivityIntent = Intent.makeMainActivity(
//        ComponentName(
//            ApplicationProvider.getApplicationContext(),
//            TestRootActivity::class.java
//        )
//    ).putExtra(FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY,themeRedId)
//
//    ActivityScenario.launch<TestRootActivity>(mainActivityIntent).onActivity { activity ->
//        fragmentFactory?.let {
//            activity.supportFragmentManager.fragmentFactory = it
//        }
//
//        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
//            Preconditions.checkNotNull(T::class.java.classLoader),
//            T::class.java.name
//        )
//
//        fragment.arguments = fragmentArgs
//
//        activity.supportFragmentManager.beginTransaction()
//            .add(android.R.id.content, fragment, "")
//            .commitNow()
//
//        (fragment as T).action()
//    }
//}
