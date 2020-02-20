package org.ieselcaminas.daniel.proyectoclase.dexInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.ieselcaminas.daniel.proyectoclase.data.Species

class DexInfoViewModel(species: Species, app: Application) : AndroidViewModel(app) {

    private val _specieDetail = MutableLiveData<Species>()
    val specieDetail: LiveData<Species>
        get() = _specieDetail

    init {
        _specieDetail.value = species
    }







}
