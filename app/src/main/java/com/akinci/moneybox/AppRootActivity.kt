package com.akinci.moneybox

import com.akinci.moneybox.common.activity.RootActivity
import com.akinci.moneybox.common.storage.IntentParams
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AppRootActivity : RootActivity() {

    /** fragment args. **/
    open val isDirectLogin by lazy { intent.getBooleanExtra(IntentParams.DIRECT_LOGIN, false)}

    override fun getNavigationGraph(): Int = R.navigation.navigation_root
    override fun getFragmentsThatHidesBackButton() = setOf(R.id.splashFragment, R.id.loginFragment, R.id.productListFragment)
}