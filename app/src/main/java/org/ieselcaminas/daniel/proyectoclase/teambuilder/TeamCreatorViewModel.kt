package org.ieselcaminas.daniel.proyectoclase.teambuilder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember


class TeamCreatorViewModel(val member: TeamMember, val application: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _teamMember = MutableLiveData<TeamMember>()
    val teamMember: LiveData<TeamMember>
        get() = _teamMember

    init {
        setDataInLayout()
    }

    private fun setDataInLayout() {
        uiScope.launch {
                _teamMember.value = member
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
