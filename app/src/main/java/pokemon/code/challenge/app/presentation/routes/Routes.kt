package pokemon.code.challenge.app.presentation.routes

import pokemon.code.challenge.app.util.MY_LOCATION_SCREEN
import pokemon.code.challenge.app.util.POKEMON_DETAIL_SCREEN_ROUTE
import pokemon.code.challenge.app.util.POKEMON_DETAIL_SCREEN_ROUTE_WITH_ID
import pokemon.code.challenge.app.util.POKEMON_LIST_SCREEN_ROUTE

sealed class Routes(val route: String) {
    object PokemonListScreen : Routes(POKEMON_LIST_SCREEN_ROUTE)
    object PokemonDetailScreen : Routes(POKEMON_DETAIL_SCREEN_ROUTE_WITH_ID) {
        fun createRoute(id: Int) = POKEMON_DETAIL_SCREEN_ROUTE.plus("/").plus(id)
    }
    object MyLocationScreen : Routes(MY_LOCATION_SCREEN)
}