package org.ieselcaminas.daniel.proyectoclase.teambuilder

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember

class TeamCreatorViewModelFactory(
    private val teamMember: TeamMember,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamCreatorViewModel::class.java))
            return TeamCreatorViewModel(teamMember, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}