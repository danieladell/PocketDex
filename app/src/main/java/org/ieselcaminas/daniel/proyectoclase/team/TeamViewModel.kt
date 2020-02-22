package org.ieselcaminas.daniel.proyectoclase.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamViewModel : ViewModel() {

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
        uiScope.launch {
                fireData.getTeamData().observeForever {
                    _teams.value = it
            }
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