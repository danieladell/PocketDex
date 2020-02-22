package org.ieselcaminas.daniel.proyectoclase.dex

import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class DexViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val fireData = FirestoreData()

    private val _navigateToSelectedSpecie = MutableLiveData<Species>()
    val navigateToSelectedSpecie: LiveData<Species>
        get() = _navigateToSelectedSpecie

    private val _species = MutableLiveData<List<Species>>()
    val species: LiveData<List<Species>>
        get() = _species

    init {
        getSpeciesFirebase()
    }

    private fun getSpeciesFirebase() {
        uiScope.launch {
                fireData.getSpecies().observeForever {
                    _species.value = it
                }
        }
    }

    fun displaySpeciesDetails(species: Species) {
        _navigateToSelectedSpecie.value = species
    }

    fun displaySpeciesDetailsComplete() {
        _navigateToSelectedSpecie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}













