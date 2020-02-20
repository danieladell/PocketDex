package org.ieselcaminas.daniel.proyectoclase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Species(
    var idD: String,
    var id_species: Long,
    var image: String,
    var name: String,
    var type1: String = "Normal",
    var type2: String = "Normal",
    var weight: Float = 0.0f,
    var height: Float = 0.0f,
    var description: String = "d"
    ): Parcelable


