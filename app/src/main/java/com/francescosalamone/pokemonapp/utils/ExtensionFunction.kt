package com.francescosalamone.pokemonapp.utils

import timber.log.Timber

fun Int.toComplementary(): Int {
    Timber.d("Color to convert $this")
    var r = this and 255
    var g = (this shr 8) and 255
    var b = (this shr 16) and 255
    val a = (this shr 24) and 255

    Timber.d("A=$a, R=$r, G=$g, b=$b")

    r = 255 - r
    g = 255 - g
    b = 255 - b

    return r or (g shl 8) or (b shl 16) or (a shl 24)
}