package pokemon.code.challenge.app.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import pokemon.code.challenge.app.R
import pokemon.code.challenge.app.data.remote.model.LocationModel
import pokemon.code.challenge.app.presentation.viewModel.PokemonViewModel

@Composable
fun MyLocationScreen(pokemonViewModel: PokemonViewModel, onBackPressed: () -> Unit) {
    val locations: List<LocationModel> by pokemonViewModel.locations.observeAsState(initial = emptyList())

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (
            locationsRef,
            mapRef
        ) = createRefs()

        LocationsView(
            modifier = Modifier
                .height(250.dp)
                .constrainAs(locationsRef, constrainBlock = {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }),
            locations
        )

        LocationMap(
            modifier = Modifier
                .constrainAs(mapRef, constrainBlock = {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(locationsRef.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }),
            locations = locations
        )
    }

    BackHandler(enabled = true, onBack = onBackPressed)
}

@Composable
private fun LocationsView(modifier: Modifier, locations: List<LocationModel>) {

    LazyColumn(
        modifier = modifier,
        content = {
            items(locations, key = { it.date }) {
                LocationsItemView(it)
            }
        }
    )
}

@Composable
private fun LocationsItemView(location: LocationModel) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (
            locationRef
        ) = createRefs()

        Text(
            text = context.getString(R.string.current_location_data, location.date, location.latitude.toString(), location.longitude.toString()),
            modifier = Modifier.constrainAs(locationRef, constrainBlock = {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
        )
    }
}

@Composable
private fun LocationMap(modifier: Modifier, locations: List<LocationModel>) {
    locations.getOrNull(0)?.let {
        val initialZoom = 6f
        val finalZoom = 15f
        val destinationLatLng = LatLng(it.latitude, it.longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(destinationLatLng, initialZoom)
        }

        LaunchedEffect(key1 = true) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(CameraPosition(destinationLatLng, finalZoom, 0f, 0f))
            )
        }

        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            content = {
                locations.forEach { location ->
                    val marker = LatLng(location.latitude, location.longitude)
                    Marker(position = marker, title = location.date)
                }
            }
        )
    }
}