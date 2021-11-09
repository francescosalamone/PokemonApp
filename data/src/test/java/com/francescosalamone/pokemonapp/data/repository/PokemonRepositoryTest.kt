package com.francescosalamone.pokemonapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescosalamone.pokemonapp.data.exception.MissingCachedDataException
import com.francescosalamone.pokemonapp.data.source.DataSource
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PokemonRepositoryTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Mock
    lateinit var remoteDataSource: DataSource

    @Mock
    lateinit var roomDataSource: DataSource

    private lateinit var repository: PokemonRepositoryImpl
    private lateinit var remotePokemonList: PokemonList
    private lateinit var roomPokemonList: PokemonList
    private lateinit var remotePokemonDetail: Pokemon
    private lateinit var roomPokemonDetail: Pokemon

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = PokemonRepositoryImpl(remoteDataSource, roomDataSource)

        remotePokemonList = PokemonList(
            count = 1,
            results = listOf(
                PokemonList.PokemonData(
                    uid = 3,
                    name = "Pikachu"
                )
            )
        )

        roomPokemonList = PokemonList(
            count = 1,
            results = listOf(
                PokemonList.PokemonData(
                    uid = 7,
                    name = "Bulbasaur"
                )
            )
        )

        remotePokemonDetail = Pokemon(
            uid = 7,
            baseExperience = 15,
            weight = 5,
            height = 150,
            name = "Pikachu"
        )

        roomPokemonDetail = Pokemon(
            uid = 1,
            baseExperience = 4,
            weight = 2,
            height = 40,
            name = "Bulbasaur"
        )
    }

    @Test
    fun calling_getPokemonList_returns_data_from_network() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(remotePokemonList)
        Mockito.`when`(roomDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenAnswer { throw MissingCachedDataException("Missing data from db") }

        val result = repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()).toList()

        assert(result.firstOrNull() is DataState.Loading)

        result.getOrNull(1)?.let {
            assert(it is DataState.Success && it.data == remotePokemonList)
        }
    }

    @Test
    fun calling_getPokemonList_returns_data_from_room() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(remotePokemonList)
        Mockito.`when`(roomDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(roomPokemonList)

        val result = repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()).toList()
        assert(result.firstOrNull() is DataState.Loading)

        result.getOrNull(1)?.let {
            assert(it is DataState.Success && it.data == roomPokemonList)
        }
    }

    @Test
    fun calling_getPokemonList_saves_data_to_room() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(remotePokemonList)
        Mockito.`when`(roomDataSource.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenAnswer { throw MissingCachedDataException("Missing data from db") }

        repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()).last()
        Mockito.verify(roomDataSource, Mockito.times(1))
            .savePokemonList(remotePokemonList)
    }

    @Test
    fun calling_getPokemon_returns_data_from_network() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemon(Mockito.anyString()))
            .thenReturn(remotePokemonDetail)
        Mockito.`when`(roomDataSource.getPokemon(Mockito.anyString()))
            .thenAnswer { throw MissingCachedDataException("Missing data from db") }

        val result = repository.getPokemon(Mockito.anyString()).toList()

        assert(result.firstOrNull() is DataState.Loading)

        result.getOrNull(1)?.let {
            assert(it is DataState.Success && it.data == remotePokemonDetail)
        }
    }

    @Test
    fun calling_getPokemon_returns_data_from_room() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemon(Mockito.anyString()))
            .thenReturn(remotePokemonDetail)
        Mockito.`when`(roomDataSource.getPokemon(Mockito.anyString()))
            .thenReturn(roomPokemonDetail)

        val result = repository.getPokemon(Mockito.anyString()).toList()
        assert(result.firstOrNull() is DataState.Loading)

        result.getOrNull(1)?.let {
            assert(it is DataState.Success && it.data == roomPokemonDetail)
        }
    }

    @Test
    fun calling_getPokemon_saves_data_to_room() = testCoroutineScope.runBlockingTest {
        Mockito.`when`(remoteDataSource.getPokemon(Mockito.anyString()))
            .thenReturn(remotePokemonDetail)
        Mockito.`when`(roomDataSource.getPokemon(Mockito.anyString()))
            .thenAnswer { throw MissingCachedDataException("Missing data from db") }

        repository.getPokemon(Mockito.anyString()).last()
        Mockito.verify(roomDataSource, Mockito.times(1))
            .savePokemon(remotePokemonDetail)
    }
}