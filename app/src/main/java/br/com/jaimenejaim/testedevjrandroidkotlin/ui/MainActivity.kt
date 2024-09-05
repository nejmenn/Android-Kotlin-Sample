package br.com.jaimenejaim.testedevjrandroidkotlin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters.DiskPermissionAdapter
import br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters.DiskPermissionAdapterImpl.BluetoothPermissionCallback
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.CheckDiskPermissionScreen
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SilverBackground
import br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel.OrderViewModel
import br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel.PermissionViewModel
import br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var diskPermissionAdapter: DiskPermissionAdapter

    private val ordersViewModel by viewModels<OrderViewModel>()
    private val productsViewModel by viewModels<ProductViewModel>()
    private val permissionViewModel by viewModels<PermissionViewModel>()

    private val diskPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            diskPermissionAdapter.checkPermissions()
        }

    private val newApiDiskPermissionRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            diskPermissionAdapter.checkPermissions()
        }

    private fun resolveResult() {
        permissionViewModel.setPermission(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        diskPermissionAdapter.setLauncher(diskPermissionRequest)
        diskPermissionAdapter.setLauncherNewApi(newApiDiskPermissionRequest)
        diskPermissionAdapter.setCallback(object :
            BluetoothPermissionCallback {
            override fun resolvePermission() {
                resolveResult()
            }
        })

        if (diskPermissionAdapter.hasPermission()) {
            diskPermissionAdapter.resolvePermission()
        }

        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val context = LocalContext.current

            val ordersState = ordersViewModel.orders.collectAsState().value
            val productsState = productsViewModel.products.collectAsState().value
            val hasDiskPermission = permissionViewModel.hasPermission.collectAsState().value
            val onCsvProductExportLoading = productsViewModel.loadCsvSaveState.collectAsState().value
            val onCsvOrderExportLoading = ordersViewModel.loadCsvSaveState.collectAsState().value

            if (hasDiskPermission) {
                HomeScreen(
                    onCsvLoading = if (onCsvProductExportLoading || onCsvOrderExportLoading) true else false,
                    currentDestination = currentDestination,
                    navController = navController,
                    modifier = Modifier
                        .background(SilverBackground),
                    ordersState = ordersState,
                    productsState = productsState,
                    loadOrders = {
                        ordersViewModel.getAll()
                    },
                    loadProducts = {
                        productsViewModel.getAll()
                    },
                    actionExportToCsv = {
                        if (diskPermissionAdapter.hasPermissionDenied()) {
                            permissionViewModel.setPermission(false)
                            return@HomeScreen
                        }

                        when (currentDestination?.route) {
                            BottomBarScreen.Product.route -> {
                                productsViewModel.exportToCsv(context = context)
                            }
                            BottomBarScreen.Order.route -> {
                               ordersViewModel.exportToCsv(context = context)
                            }
                        }

                    }
                )
            } else {
                CheckDiskPermissionScreen(
                    modifier = Modifier
                        .background(SilverBackground),
                    openPermissionRequest = {
                        diskPermissionAdapter.requestPermissions()
                    },
                    skipePermission = {
                        diskPermissionAdapter.resolvePermission()
                    }
                )
            }
        }
    }
}
