package com.francescosalamone.pokemonapp

import com.francescosalamone.pokemonapp.utils.filterIsInstanceOrNull
import org.junit.Test

class ExtensionFunctionTest {

    private val multipleTypeList = listOf("Test", 1,100F, mapOf("K" to "V"))

    @Test
    fun filterIsInstance_returns_only_items_with_chosen_type() {
        val filteredList = multipleTypeList.filterIsInstanceOrNull<String>()
        assert(filteredList?.size == 1 && filteredList.firstOrNull() == "Test" )
    }

    @Test
    fun filterIsInstance_returns_null_if_type_not_found() {
        val filteredList = multipleTypeList.filterIsInstanceOrNull<Boolean>()
        assert(filteredList == null)
    }
}