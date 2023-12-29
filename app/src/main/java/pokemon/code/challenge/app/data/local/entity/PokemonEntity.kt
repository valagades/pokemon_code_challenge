package pokemon.code.challenge.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pokemon.code.challenge.app.util.POKEMON_ENTITY
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_FAVORITE
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_HAS_DETAIL
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_HEIGHT
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_ID
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_IMAGE
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_NAME
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_TYPE_ONE
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_TYPE_TWO
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_WEIGHT

@Entity(tableName = POKEMON_ENTITY)
data class PokemonEntity(

    @PrimaryKey
    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_ID)
    val id: Int,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_IMAGE)
    val image: String,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_HEIGHT)
    val height: Double,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_WEIGHT)
    val weight: Double,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_TYPE_ONE)
    val typeOne: String,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_TYPE_TWO)
    val typeTwo: String,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_FAVORITE)
    val favorite: Boolean,

    @ColumnInfo(name = POKEMON_ENTITY_COLUMN_HAS_DETAIL)
    val hasDetail: Boolean
)
