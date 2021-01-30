package com.akinci.moneybox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.akinci.moneybox.feaure.login.view.LoginFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@SmallTest
@HiltAndroidTest
class ExampleInstrumentedTest {

    // this rule provides hilt injection
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // this rule provides coroutines process sequentially in the scope
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject() // with this command hilt injects parameters
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testFragmentInHiltContainer(){
        /** Commented out needs HiltExt.kt **/
//        launchFragmentInHiltContainer<LoginFragment> {
//
//        }
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.akinci.moneybox", appContext.packageName)
    }

    @After
    fun tearDown(){

    }
}