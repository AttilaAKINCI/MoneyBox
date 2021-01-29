package com.akinci.moneybox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.akinci.moneybox.common.component.SnackBar
import com.akinci.moneybox.common.storage.IntentParams
import com.akinci.moneybox.databinding.ActivityRootBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private lateinit var binding : ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root)

        // setup navigation
        navigationController = findNavController(R.id.nav_host_fragment)
        // tell navigation controller that which fragments will be at the top of backstack
        // (hides backbutton for fragments which are placed at top)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.splashFragment, R.id.loginFragment, R.id.productListFragment)
        )

        // remove extra padding between arrow and toolbar title
        binding.toolbar.contentInsetStartWithNavigation = 10

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        // direct login case for 401 unAuthorized service calls
        val isDirectLogin = intent.getBooleanExtra(IntentParams.DIRECT_LOGIN, false)
        if(isDirectLogin){
            SnackBar.make(binding.root, "Your login session is expired. Please login again.", SnackBar.LENGTH_LONG).show()
        }

    }
}