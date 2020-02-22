package org.ieselcaminas.daniel.proyectoclase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamMember(
    val idD: String,
    val id_team: String,
    val name: String,
    val ability: String,
    val evs: List<String>,
    val item: String,
    val moves: List<String>
): Parcelable
