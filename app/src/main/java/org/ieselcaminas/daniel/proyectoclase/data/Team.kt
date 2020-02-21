package org.ieselcaminas.daniel.proyectoclase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val id: String,
    val name: String,
    val members: List<TeamMember>
): Parcelable

