package org.ieselcaminas.daniel.proyectoclase.dex

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.dexInfo.DexInfoViewModel

class DexInfoViewModelFactory(
    private val specie: Species,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DexInfoViewModel::class.java)) {
            return DexInfoViewModel(specie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}