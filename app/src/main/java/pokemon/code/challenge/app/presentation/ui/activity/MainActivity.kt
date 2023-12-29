package pokemon.code.challenge.app.presentation.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pokemon.code.challenge.app.presentation.routes.Routes
import pokemon.code.challenge.app.presentation.ui.screen.MyLocationScreen
import pokemon.code.challenge.app.presentation.ui.screen.PokemonDetailScreen
import pokemon.code.challenge.app.presentation.ui.screen.PokemonListScreen
import pokemon.code.challenge.app.presentation.ui.theme.PokemonCodeChallengeAppTheme
import pokemon.code.challenge.app.presentation.viewModel.PokemonViewModel
import pokemon.code.challenge.app.service.LocationService
import pokemon.code.challenge.app.util.APP_TAG
import pokemon.code.challenge.app.util.checkUser
import pokemon.code.challenge.app.util.getUser
import pokemon.code.challenge.app.util.hasLocationPermission
import pokemon.code.challenge.app.util.hasPostNotificationPermission

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModels()

    private var serviceStarted = false
    private val permissions = mutableListOf<String>().apply {
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
        add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUser()
        checkLocationPermission()
        setContent {
            PokemonCodeChallengeAppTheme(darkTheme = false) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navHostController = rememberNavController()
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = {
                            val coarseLocationGranted = it[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
                            val fineLocation = it[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
                            Log.i(APP_TAG, "launcher - onResult coarseLocationGranted:$coarseLocationGranted - fineLocation:$fineLocation")
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                val postNotification = it[Manifest.permission.POST_NOTIFICATIONS] ?: false
                                if (coarseLocationGranted && fineLocation && postNotification) {
                                    navHostController.navigate(Routes.MyLocationScreen.route)
                                }
                            } else {
                                if (coarseLocationGranted && fineLocation) {
                                    navHostController.navigate(Routes.MyLocationScreen.route)
                                }
                            }
                        }
                    )

                    NavHost(navController = navHostController, startDestination = Routes.PokemonListScreen.route) {
                        composable(Routes.PokemonListScreen.route) {
                            PokemonListScreen(navHostController, pokemonViewModel) {
                                finish()
                            }
                        }

                        composable(
                            route = Routes.PokemonDetailScreen.route,
                            arguments = listOf(navArgument("id", builder = { type = NavType.IntType }))
                        ) { navBackStackEntry ->
                            PokemonDetailScreen(navBackStackEntry.arguments?.getInt("id") ?: 0, pokemonViewModel) {
                                navHostController.navigate(Routes.PokemonListScreen.route)
                            }
                        }

                        composable(Routes.MyLocationScreen.route) {
                            Log.i(APP_TAG, "composable MyLocationScreen.route")
                            if (hasLocationPermission()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    if (hasPostNotificationPermission()) {
                                        if (serviceStarted.not()) {
                                            startLocationService()
                                        }
                                        MyLocationScreen(pokemonViewModel) {
                                            navHostController.navigate(Routes.PokemonListScreen.route)
                                        }
                                    } else {
                                        launcher.launch(permissions.toTypedArray())
                                    }
                                } else {
                                    if (serviceStarted.not()) {
                                        startLocationService()
                                    }
                                    MyLocationScreen(pokemonViewModel) {
                                        navHostController.navigate(Routes.PokemonListScreen.route)
                                    }
                                }
                            } else {
                                launcher.launch(permissions.toTypedArray())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        stopLocationService()

        super.onDestroy()
    }

    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && hasPostNotificationPermission().not()) return

        if (hasLocationPermission()) {
            startLocationService()
        }
    }

    private fun startLocationService() {
        serviceStarted = true
        pokemonViewModel.startLocationListener(getUser())
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }

    private fun stopLocationService() {
        serviceStarted = false
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
    }
}