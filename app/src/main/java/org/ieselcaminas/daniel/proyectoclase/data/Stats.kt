package org.ieselcaminas.daniel.proyectoclase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stats(
    val idD: String,
    val id_stat: Int,
    val species: String,
    val hp: Int,
    val atk: Int,
    val dfs: Int,
    val sp_atk: Int,
    val sp_dfs: Int,
    val spd: Int
): Parcelable
