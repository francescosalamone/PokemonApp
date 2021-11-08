package com.francescosalamone.pokemonapp.domain.utils

import com.francescosalamone.pokemonapp.model.base.UseCase
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

inline fun <reified T, I, O> Module.provideUseCase(
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> where T: UseCase<I, O> =
    single(qualifier, createdAtStart, definition)