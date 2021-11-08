package com.francescosalamone.pokemonapp

import com.francescosalamone.pokemonapp.navigation.Navigator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times

class NavigatorTest {

    private var navigator = Navigator<Int>()

    @Mock
    private lateinit var utilTest: UtilTest

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        navigator.navigate(1, utilTest::emptyAction)
    }

    @Test
    fun back_returns_true_if_can_go_back_test() {
        navigator.navigate(2){}
        assertTrue(navigator.back())
    }

    @Test
    fun back_returns_false_if_can_not_go_back_test() {
        assertFalse(navigator.back())
    }

    @Test
    fun back_go_to_previous_page_test() {
        navigator.navigate(2){}
        navigator.back()
        Mockito.verify(utilTest, times(2)).emptyAction()
    }

    @Test
    fun back_does_not_go_to_current_page_test() {
        navigator.navigate(2, utilTest::otherEmptyAction)
        navigator.back()
        Mockito.verify(utilTest, times(1)).otherEmptyAction()
    }
}