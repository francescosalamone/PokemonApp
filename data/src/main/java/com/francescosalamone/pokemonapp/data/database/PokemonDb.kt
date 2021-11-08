package com.francescosalamone.pokemonapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.francescosalamone.pokemonapp.data.converters.PokemonConverter
import com.francescosalamone.pokemonapp.data.dao.PokemonDao
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList

@Database(
    entities = [
        PokemonList.PokemonData::class,
        Pokemon::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonConverter::class)
abstract class PokemonDb: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile
        private var INSTANCE: PokemonDb? = null

        fun getPokemonDb(
            context: Context,
            pokemonConverter: PokemonConverter
        ): PokemonDb {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                            context.applicationContext,
                            PokemonDb::class.java,
                            "pokemon_database"
                        )
                        .addTypeConverter(pokemonConverter)
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}