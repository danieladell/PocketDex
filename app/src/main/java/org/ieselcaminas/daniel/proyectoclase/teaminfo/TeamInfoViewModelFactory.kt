package org.ieselcaminas.daniel.proyectoclase.teaminfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TeamInfoViewModelFactory(
    private val id_team: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamInfoViewModel::class.java)) {
            return TeamInfoViewModel(id_team, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}