package pokemon.code.challenge.app.presentation.ui.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pokemon.code.challenge.app.R
import pokemon.code.challenge.app.presentation.mapper.toEntity
import pokemon.code.challenge.app.presentation.model.Pokemon
import pokemon.code.challenge.app.presentation.model.PokemonUIState
import pokemon.code.challenge.app.presentation.model.UIState
import pokemon.code.challenge.app.presentation.routes.Routes
import pokemon.code.challenge.app.presentation.ui.custom.ProgressIndicator
import pokemon.code.challenge.app.presentation.ui.custom.SpriteImage
import pokemon.code.challenge.app.presentation.viewModel.PokemonViewModel
import pokemon.code.challenge.app.util.isOnline

@Composable
fun PokemonListScreen(navHostController: NavHostController, pokemonViewModel: PokemonViewModel, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val coroutineScope = rememberCoroutineScope()
    val uiState by pokemonViewModel.uIState().observeAsState(initial = UIState.Complete)
    var showDialog by remember { mutableStateOf(false) }

    val pokemonsState by produceState<PokemonUIState>(
        initialValue = PokemonUIState.Loading,
        key1 = lifecycle,
        key2 = pokemonViewModel,
        producer = {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                pokemonViewModel.pokemonsState.collect { value = it }
            }
        }
    )

    when (pokemonsState) {
        is PokemonUIState.Loading -> showDialog = true
        is PokemonUIState.Error -> {
            (pokemonsState as? PokemonUIState.Error)?.let {
                coroutineScope.launch {
                    Toast.makeText(context, context.getString(R.string.error_message, it.throwable.message), Toast.LENGTH_SHORT).show()
                }
            }
        }
        is PokemonUIState.Complete -> {
            (pokemonsState as? PokemonUIState.Complete)?.pokemons?.let {
                if (it.isEmpty()) {
                    if (context.isOnline()) {
                        pokemonViewModel.getList(0)
                    } else {
                        pokemonViewModel.updateState(UIState.Error(context.getString(R.string.connection_error)))
                    }
                } else {
                    PokemonList(pokemons = it, navHostController, pokemonViewModel)
                }
            }
        }
    }

    when (uiState) {
        is UIState.Loading -> showDialog = true

        is UIState.Error -> {
            showDialog = false
            (uiState as? UIState.Error)?.let {
                coroutineScope.launch {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        is UIState.Complete -> showDialog = false
    }

    if (showDialog) {
        ProgressIndicator { showDialog = it }
    }

    BackHandler(enabled = true, onBack = onBackPressed)
}

@Composable
private fun PokemonList(pokemons: List<Pokemon>, navHostController: NavHostController, pokemonViewModel: PokemonViewModel) {
    val context = LocalContext.current
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = pokemonViewModel.listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
            }
        }
    }
    val itemCount by remember {
        derivedStateOf {
            pokemonViewModel.listState.layoutInfo.totalItemsCount
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (
            myLocationsRef,
            listRef
        ) = createRefs()

        Button(
            onClick = { navHostController.navigate(Routes.MyLocationScreen.route) },
            modifier = Modifier.constrainAs(myLocationsRef, constrainBlock = {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }),
            content = {
                Text(text = context.getString(R.string.my_locations))
            }
        )

        LazyColumn(
            state = pokemonViewModel.listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.constrainAs(listRef, constrainBlock = {
                bottom.linkTo(myLocationsRef.top)
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }),
            content = {
                items(pokemons, key = { it.id }) { pokemon ->
                    PokemonListItem(pokemon, navHostController, pokemonViewModel)
                }
            })
    }

    if (isAtBottom) {
        if (context.isOnline()) {
            pokemonViewModel.getList(itemCount)
        } else {
            pokemonViewModel.updateState(UIState.Error(context.getString(R.string.connection_error)))
        }
    }
}

@Composable
private fun PokemonListItem(pokemon: Pokemon, navHostController: NavHostController, pokemonViewModel: PokemonViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navHostController.navigate(Routes.PokemonDetailScreen.createRoute(pokemon.id)) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            content = {
                val (
                    spriteRef,
                    favoriteRef,
                    nameRef
                ) = createRefs()

                SpriteImage(
                    name = pokemon.name,
                    url = pokemon.image,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .constrainAs(spriteRef, constrainBlock = {
                            start.linkTo(parent.start, margin = 16.dp)
                            top.linkTo(parent.top, margin = 16.dp)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        })
                )

                Icon(
                    imageVector = if (pokemon.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .constrainAs(favoriteRef, constrainBlock = {
                            end.linkTo(parent.end, margin = 16.dp)
                            top.linkTo(parent.top, margin = 16.dp)
                        })
                        .clickable {
                            val pokemonToUpdate = pokemon.copy(favorite = pokemon.favorite.not())
                            pokemonViewModel.update(pokemonToUpdate.toEntity())
                        }
                )

                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.constrainAs(nameRef, constrainBlock = {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(spriteRef.end, margin = 8.dp)
                        end.linkTo(favoriteRef.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    })
                )
            }
        )
    }
}