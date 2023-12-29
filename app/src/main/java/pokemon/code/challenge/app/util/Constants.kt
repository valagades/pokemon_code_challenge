package pokemon.code.challenge.app.util

// Common
const val APP_TAG = "PokemonChallengeTag"
const val DATE_FORMAT_PATTERN_YYYY_MM_DD_H_MM_SS = "yyyy-MM-dd HH:mm:ss"

// Database
const val POKEMON_DATABASE_NAME = "pokemon_challenge_db"
const val POKEMON_ENTITY = "pokemon"
const val POKEMON_ENTITY_COLUMN_NAME = "name"
const val POKEMON_ENTITY_COLUMN_IMAGE = "image"
const val POKEMON_ENTITY_COLUMN_FAVORITE = "favorite"
const val POKEMON_ENTITY_COLUMN_ID = "id"
const val POKEMON_ENTITY_COLUMN_HEIGHT = "height"
const val POKEMON_ENTITY_COLUMN_WEIGHT = "weight"
const val POKEMON_ENTITY_COLUMN_TYPE_ONE = "type_one"
const val POKEMON_ENTITY_COLUMN_TYPE_TWO = "type_two"
const val POKEMON_ENTITY_COLUMN_HAS_DETAIL = "has_detail"

// Api
const val POKEMON_BASE_URL = "https://pokeapi.co"
const val POKEMON_GET = "/api/v2/pokemon"
const val POKEMON_GET_QUERY_OFFSET = "offset"
const val POKEMON_GET_QUERY_LIMIT = "limit"
const val POKEMON_GET_DETAIL = "/api/v2/pokemon/{id}"
const val POKEMON_GET_DETAIL_PATH_ID = POKEMON_ENTITY_COLUMN_ID
const val POKEMON_IMAGE_URL_RAW = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
const val POKEMON_RESULT = "results"
const val POKEMON_RESULT_NAME = POKEMON_ENTITY_COLUMN_NAME
const val POKEMON_RESULT_URL = "url"
const val POKEMON_DETAIL_RESULT_HEIGHT = POKEMON_ENTITY_COLUMN_HEIGHT
const val POKEMON_DETAIL_RESULT_WEIGHT = POKEMON_ENTITY_COLUMN_WEIGHT
const val POKEMON_DETAIL_RESULT_TYPES = "types"
const val POKEMON_DETAIL_RESULT_TYPE = "type"
const val POKEMON_DETAIL_RESULT_NAME = POKEMON_ENTITY_COLUMN_NAME

// Screens
const val POKEMON_LIST_SCREEN_ROUTE = "Pokemon_List_Screen"
const val POKEMON_DETAIL_SCREEN_ROUTE = "Pokemon_Detail_Screen"
const val POKEMON_DETAIL_SCREEN_ROUTE_WITH_ID = POKEMON_DETAIL_SCREEN_ROUTE.plus("/{id}")
const val MY_LOCATION_SCREEN = "My_Location_Screen"

// Shared preferences
const val SHARED_PREFERENCES_FILE_NAME = "pokemon_prefs"
const val SHARED_PREFERENCES_USER = "user"

// Firebase
const val USER_COLLECTION = "users"
const val USER_COLLECTION_LOCATIONS_FIELD = "locations"
const val USER_COLLECTION_LOCATIONS_LATITUDE_FIELD = "latitude"
const val USER_COLLECTION_LOCATIONS_LONGITUDE_FIELD = "longitude"