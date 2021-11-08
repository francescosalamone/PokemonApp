package com.francescosalamone.pokemonapp.data.converters

import com.francescosalamone.pokemonapp.data.di.dataModuleTest
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.squareup.moshi.Moshi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.mockito.MockitoAnnotations

class PokemonConverterTest {

    private val moshi: Moshi by inject(Moshi::class.java)
    private lateinit var converter: PokemonConverter
    private lateinit var deserializedAbilities: List<Pokemon.AbilityData>
    private lateinit var serializedAbilities: String
    private lateinit var deserializedType: List<Pokemon.TypeWrapper>
    private lateinit var serializedType: String
    private lateinit var deserializedStats: List<Pokemon.StatWrapper>
    private lateinit var serializedStats: String
    private lateinit var deserializedSprites: Pokemon.Sprites
    private lateinit var serializedSprites: String

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        startKoin { modules(dataModuleTest) }

        converter = PokemonConverter(moshi)

        deserializedAbilities = listOf(
            Pokemon.AbilityData(
                ability = Pokemon.AbilityData.Ability(
                    name = "overgrow",
                    url = "https://pokeapi.co/api/v2/ability/65/"
                ),
                isHidden = false,
                slot = 1
            )
        )
        serializedAbilities = "[{\"ability\":{\"name\":\"overgrow\",\"url\":\"https://pokeapi.co/api/v2/ability/65/\"},\"is_hidden\":false,\"slot\":1}]"

        deserializedType = listOf(
            Pokemon.TypeWrapper(
                slot = 1,
                type = Pokemon.TypeWrapper.TypeDetail(
                    name = Pokemon.TypeWrapper.TypeDetail.Type.GRASS,
                    url = "https://pokeapi.co/api/v2/type/12/"
                )
            )
        )
        serializedType = "[{\"slot\":1,\"type\":{\"name\":\"grass\",\"url\":\"https://pokeapi.co/api/v2/type/12/\"}}]"

        deserializedStats = listOf(
            Pokemon.StatWrapper(
                baseStat = 45,
                effort = 0,
                stat = Pokemon.StatWrapper.StatDetail(
                    type = Pokemon.StatWrapper.StatDetail.Stat.HP,
                    url = "https://pokeapi.co/api/v2/stat/1/"
                )
            )
        )
        serializedStats = "[{\"base_stat\":45,\"effort\":0,\"stat\":{\"name\":\"hp\",\"url\":\"https://pokeapi.co/api/v2/stat/1/\"}}]"

        deserializedSprites = Pokemon.Sprites(
            backDefault = "1.png",
            frontShinyFemale = null,
            other = Pokemon.Sprites.Other(
                officialArtwork = Pokemon.Sprites.Other.OfficialArtWork(
                    frontDefault = "1.png"
                )
            )
        )

        serializedSprites = "{\"back_default\":\"1.png\",\"other\":{\"official-artwork\":{\"front_default\":\"1.png\"}}}"
    }

    @Test
    fun toAbilityList_returns_deserialized_data() {
        val converted = converter.toAbilityList(serializedAbilities)
        assert(converted == deserializedAbilities)
    }

    @Test
    fun fromAbilityList_returns_serialized_data() {
        val converted = converter.fromAbilityList(deserializedAbilities)
        assert(converted == serializedAbilities)
    }

    @Test
    fun toTypeList_returns_deserialized_data() {
        val converted = converter.toTypeList(serializedType)
        assert(converted == deserializedType)
    }

    @Test
    fun fromTypeList_returns_serialized_data() {
        val converted = converter.fromTypeList(deserializedType)
        assert(converted == serializedType)
    }

    @Test
    fun toStatsList_returns_deserialized_data() {
        val converted = converter.toStatsList(serializedStats)
        assert(converted == deserializedStats)
    }

    @Test
    fun fromStatsList_returns_serialized_data() {
        val converted = converter.fromStatsList(deserializedStats)
        assert(converted == serializedStats)
    }

    @Test
    fun toSprites_returns_deserialized_data() {
        val converted = converter.toSprites(serializedSprites)
        assert(converted == deserializedSprites)
    }

    @Test
    fun fromSprites_returns_serialized_data() {
        val converted = converter.fromSprites(deserializedSprites)
        assert(converted == serializedSprites)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}