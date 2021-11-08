package com.francescosalamone.pokemonapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.francescosalamone.pokemonapp.domain.usecase.PokemonDetailUseCase
import com.francescosalamone.pokemonapp.model.base.UseCase
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonDataFlow
import io.uniflow.android.livedata.states
import io.uniflow.core.dispatcher.UniFlowDispatcher
import io.uniflow.core.flow.data.UIState
import io.uniflow.test.dispatcher.TestDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDataFlowTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testDispatchers: TestDispatchers = TestDispatchers(testCoroutineDispatcher)

    @Mock
    private lateinit var listUseCase: UseCase<Unit, PokemonList>

    @Mock
    private lateinit var detailUseCase: UseCase<PokemonDetailUseCase.Param, Pokemon>

    @Mock
    private lateinit var observer: Observer<UIState>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<PokemonState<*>>

    private lateinit var dataFlow: PokemonDataFlow
    private lateinit var testListResult: PokemonList
    private lateinit var failureResult: Exception
    private lateinit var testPokemonResult: Pokemon

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        UniFlowDispatcher.dispatcher = testDispatchers

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
        failureResult = Exception("TestException")

        testPokemonResult = Pokemon(
            uid = 1,
            name = "Pikachu",
            height = 170,
            weight = 5,
            types = listOf(
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.WATER,
                        url = "testUrl"
                    )
                )
            )
        )

        dataFlow = PokemonDataFlow(listUseCase, detailUseCase)
    }

    @Test
    fun first_emitted_state_is_PokemonState_Init_test() {
        dataFlow.states.observeForever(observer)
        Mockito.verify(observer, Mockito.times(1))
            .onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.first() is PokemonState.Init)
    }

    @Test
    fun calling_fetchPokemon_returns_success_test() {
        val flow = MutableStateFlow(DataState.Success(testListResult))
        Mockito.`when`(listUseCase.invoke(Unit))
            .thenReturn(flow)

        dataFlow.states.observeForever(observer)
        dataFlow.fetchPokemons()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.first() is PokemonState.Init)
        assert(argumentCaptor.allValues[1] is PokemonState.PokemonResult)
    }

    @Test
    fun calling_fetchPokemon_returns_failure_test() {
        val flow = MutableStateFlow(DataState.Failure(failureResult))
        Mockito.`when`(listUseCase.invoke(Unit))
            .thenReturn(flow)

        dataFlow.states.observeForever(observer)
        dataFlow.fetchPokemons()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.first() is PokemonState.Init)
        assert(argumentCaptor.allValues[1] is PokemonState.Failure)
    }

    @Test
    fun calling_getPokemonDetail_returns_success_test() {
        val flow = MutableStateFlow(DataState.Success(testPokemonResult))
        Mockito.`when`(detailUseCase.invoke(any()))
            .thenReturn(flow)

        dataFlow.states.observeForever(observer)
        dataFlow.getPokemonDetail(Mockito.anyString())

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.first() is PokemonState.Init)
        assert(argumentCaptor.allValues[1] is PokemonState.PokemonResult)
    }

    @Test
    fun calling_getPokemonDetail_returns_failure_test() {
        val flow = MutableStateFlow(DataState.Failure(failureResult))
        Mockito.`when`(detailUseCase.invoke(any()))
            .thenReturn(flow)

        dataFlow.states.observeForever(observer)
        dataFlow.getPokemonDetail(Mockito.anyString())

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.first() is PokemonState.Init)
        assert(argumentCaptor.allValues[1] is PokemonState.Failure)
    }

    @After
    fun tearDown() {
        dataFlow.states.removeObserver(observer)
    }
}