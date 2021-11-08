package com.francescosalamone.pokemonapp.data.exception

class MissingCachedDataException: Exception {
    constructor(message: String?): super(message)
    constructor(message: String?, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)
}