package org.ieselcaminas.daniel.proyectoclase.teaminfo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamInfoViewModel(id_team: String, val application: Application) : ViewModel() {

    private val fireData = FirestoreData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedTeamMember = MutableLiveData<TeamMember>()
    val navigateToSelectedTeamMember: LiveData<TeamMember>
        get() = _navigateToSelectedTeamMember

    private val _teamMember = MutableLiveData<List<TeamMember>>()
    val teams: LiveData<List<TeamMember>>
        get() = _teamMember

    init {
        getTeamMembersFirebase(id_team)
    }

    fun getTeamMembersFirebase(id: String) {
        fireData.getTeamMembersData(id).observeForever {
            _teamMember.value = it
        }
    }

    fun displayTeamMemberDetails(teamMember: TeamMember) {
        _navigateToSelectedTeamMember.value = teamMember
    }

    fun displayTeamMemberDetailsComplete() {
        _navigateToSelectedTeamMember.value = null
    }

}
