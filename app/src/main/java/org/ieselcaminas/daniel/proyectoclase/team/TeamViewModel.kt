package org.ieselcaminas.daniel.proyectoclase.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamViewModel: ViewModel() {

    private val fireData = FirestoreData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedTeam = MutableLiveData<Team>()
    val navigateToSelectedTeam: LiveData<Team>
        get() = _navigateToSelectedTeam

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>>
        get() = _teams

    init {
        getTeamsFirebase()
    }

    private fun getTeamsFirebase() {
        fireData.getTeamData().observeForever {
            val list = ArrayList<Team>()
            list.add(Team("1", "hola", ArrayList()))
            _teams.value = it

        }
    }

    fun displayTeamDetails(team: Team) {
        _navigateToSelectedTeam.value = team
    }

    fun displayTeamDetailsComplete() {
        _navigateToSelectedTeam.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}