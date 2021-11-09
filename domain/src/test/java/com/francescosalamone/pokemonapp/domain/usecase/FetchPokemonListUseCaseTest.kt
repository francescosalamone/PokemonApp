package com.francescosalamone.pokemonapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
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
class FetchPokemonListUseCaseTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Mock
    private lateinit var repository: PokemonRepository

    private lateinit var useCase: FetchPokemonListUseCase
    private lateinit var testListResult: PokemonList
    private val limit = 100
    private val initialPage = 0

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        useCase = FetchPokemonListUseCase(repository, initialPage, limit)

        testListResult = PokemonList(
            count = 1,
            results = listOf(
                PokemonList.PokemonData(
                    uid = 0,
                    name = "Pikachu",
                    url = "testUrl"
                )
            )
        )
    }

    @Test
    fun fetching_data_first_time_has_offset_0() = testCoroutineScope.runBlockingTest {
        val flow = MutableStateFlow(DataState.Success(testListResult))
        Mockito.`when`(repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(flow)

        useCase.invoke(Unit).first()
        Mockito.verify(repository, Mockito.times(1)).getPokemonList(limit, 0)
    }

    @Test
    fun fetching_data_increments_offset_on_success() = testCoroutineScope.runBlockingTest {
        val flow = MutableStateFlow(DataState.Success(testListResult))
        Mockito.`when`(repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(flow)

        for(i in 0..4) {
            useCase.invoke(Unit).first()
            Mockito.verify(repository, Mockito.times(1)).getPokemonList(limit, i * limit)
        }
    }

    @Test
    fun fetching_data_does_not_increments_offset_on_failure() = testCoroutineScope.runBlockingTest {
        val flow = MutableStateFlow(DataState.Failure(Exception()))
        Mockito.`when`(repository.getPokemonList(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(flow)

        for(i in 0..1) {
            useCase.invoke(Unit).first()
        }
        Mockito.verify(repository, Mockito.times(2)).getPokemonList(limit, 0)
    }
}