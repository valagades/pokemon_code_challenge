package pokemon.code.challenge.app.presentation.ui.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import pokemon.code.challenge.app.R
import pokemon.code.challenge.app.presentation.mapper.toEntity
import pokemon.code.challenge.app.presentation.model.Pokemon
import pokemon.code.challenge.app.presentation.model.PokemonDetailUIState
import pokemon.code.challenge.app.presentation.model.UIState
import pokemon.code.challenge.app.presentation.ui.custom.ProgressIndicator
import pokemon.code.challenge.app.presentation.ui.custom.SpriteImage
import pokemon.code.challenge.app.presentation.viewModel.PokemonViewModel
import pokemon.code.challenge.app.util.isOnline

@Composable
fun PokemonDetailScreen(pokemonId: Int, pokemonViewModel: PokemonViewModel, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val coroutineScope = rememberCoroutineScope()
    val uiState by pokemonViewModel.uIState().observeAsState(initial = UIState.Complete)
    var showDialog by remember { mutableStateOf(false) }

    val pokemonDetailUIState by produceState<PokemonDetailUIState?>(
        initialValue = PokemonDetailUIState.DetailLoading,
        key1 = lifecycle,
        key2 = pokemonViewModel,
        producer = {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                pokemonViewModel.getDetailById(id = pokemonId).collect { value = it }
            }
        }
    )

    pokemonDetailUIState?.let { detailUIState ->
        when (detailUIState) {
            is PokemonDetailUIState.DetailLoading -> {
                ProgressIndicator { showDialog = false }
            }

            is PokemonDetailUIState.DetailError -> {
                (detailUIState as? PokemonDetailUIState.DetailError)?.let {
                    coroutineScope.launch {
                        Toast.makeText(context, context.getString(R.string.error_message, it.throwable.message), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            is PokemonDetailUIState.DetailSuccess -> {
                (detailUIState as? PokemonDetailUIState.DetailSuccess)?.pokemon?.let { pokemon ->
                    if (pokemon.hasDetail) {
                        PokemonDetail(pokemon = pokemon, pokemonViewModel)
                    } else {
                        if (context.isOnline()) {
                            pokemonViewModel.getDetail(pokemon)
                        } else {
                            pokemonViewModel.updateState(UIState.Error(context.getString(R.string.connection_error)))
                        }
                    }
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

    BackHandler(enabled = true, onBack = onBackPressed)
}

@Composable
private fun PokemonDetail(pokemon: Pokemon, pokemonViewModel: PokemonViewModel) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        content = {
            val (
                spriteRef,
                favoriteRef,
                nameRef,
                heightRef,
                weightRef,
                typeOneRef,
                typeTwoRef
            ) = createRefs()

            SpriteImage(
                name = pokemon.name,
                url = pokemon.image,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .constrainAs(spriteRef, constrainBlock = {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, margin = 16.dp)
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
                text = context.getString(R.string.pokemon_number_and_name, pokemon.id, pokemon.name),
                modifier = Modifier.constrainAs(nameRef, constrainBlock = {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(spriteRef.bottom, margin = 16.dp)
                })
            )

            Text(
                text = context.getString(R.string.pokemon_height, pokemon.height.toString()),
                modifier = Modifier.constrainAs(heightRef, constrainBlock = {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(nameRef.bottom, margin = 16.dp)
                })
            )

            Text(
                text = context.getString(R.string.pokemon_weight, pokemon.weight.toString()),
                modifier = Modifier.constrainAs(weightRef, constrainBlock = {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(heightRef.bottom, margin = 16.dp)
                })
            )

            Text(
                text = context.getString(R.string.pokemon_types, pokemon.typeOne),
                modifier = Modifier.constrainAs(typeOneRef, constrainBlock = {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(weightRef.bottom, margin = 16.dp)
                })
            )

            if (pokemon.typeTwo.isNotEmpty()) {
                Text(
                    text = context.getString(R.string.pokemon_type_two, pokemon.typeTwo),
                    modifier = Modifier.constrainAs(typeTwoRef, constrainBlock = {
                        start.linkTo(typeOneRef.end)
                        top.linkTo(weightRef.bottom, margin = 16.dp)
                    })
                )
            }
        }
    )
}