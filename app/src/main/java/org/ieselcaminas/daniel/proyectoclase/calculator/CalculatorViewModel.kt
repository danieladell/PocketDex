package org.ieselcaminas.daniel.proyectoclase.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.ieselcaminas.daniel.proyectoclase.Nature
import org.ieselcaminas.daniel.proyectoclase.data.Stats
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData
import java.util.*
import kotlin.collections.ArrayList

class CalculatorViewModel : ViewModel() {

    private val fireData = FirestoreData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _speciesName = MutableLiveData<List<String>>()
    val speciesName: LiveData<List<String>>
        get() = _speciesName

    private val _stats = MutableLiveData<List<Stats>>()
    val stats: LiveData<List<Stats>>
        get() = _stats

    init {
        getStatsBySpecies()
    }

    private fun getStatsBySpecies() {
        fireData.getStats().observeForever {
            _stats.value = it
            val species = arrayListOf<String>()
            for(i in it) {
                species.add(i.species)
            }
            _speciesName.value = species
        }
    }

    fun calculateIvs(species: String, hp: Int, atk: Int, def: Int, spAtk: Int, spDef: Int, spd: Int, level: Int, nature: String): List<Int> {
        val listIvs = ArrayList<Int>()
        val ev = 0

        listIvs.add(calculateHpIvs(species, hp, ev, level))
        listIvs.add(calculateOtherStats(species, atk, ev, level, "atk", nature))
        listIvs.add(calculateOtherStats(species, def, ev, level, "def", nature))
        listIvs.add(calculateOtherStats(species, spAtk, ev, level, "spatk", nature))
        listIvs.add(calculateOtherStats(species, spDef, ev, level, "spdef", nature))
        listIvs.add(calculateOtherStats(species, spd, ev, level, "spd", nature))

        return listIvs
    }

    private fun calculateOtherStats(species: String, stat: Int, ev: Int, level: Int, statName: String, nature: String): Int {
        var iv = 0f
        var statBase = 0

        stats.observeForever {
            for(i in it) {
                if (i.species == species) {
                    when(statName) {
                        "atk" -> {statBase = i.atk}
                        "def" -> {statBase = i.dfs}
                        "spatk" -> {statBase = i.sp_atk}
                        "spdef" -> {statBase = i.sp_dfs}
                        "spd" -> {statBase = i.spd}
                    }
                    iv = ((stat/natureBoostDebuff(statName, nature) - 5) * 100) / level - 2*statBase - ev/4
                }
            }
        }
        return iv.toInt()
    }

    private fun calculateHpIvs(species: String, hp: Int, ev: Int, level: Int): Int {
        var iv = 0
        stats.observeForever {
            for(i in it) {
                if (i.species == species) {
                    iv = ((hp - 10) * 100) / level - 2*i.hp - ev/4 - 100
                }
            }
        }
        return iv
    }

    private fun natureBoostDebuff(statName: String, natures: String): Float {
        val nature = natures.toUpperCase(Locale.ENGLISH)

        if(statName == "atk" && (Nature.ADAMANT.toString() == nature || Nature.BRAVE.toString() == nature || Nature.LONELY.toString() == nature || Nature.NAUGHTY.toString() == nature)) {
            return 1.1f
        }
        if(statName == "def" && (Nature.BOLD.toString() == nature || Nature.RELAXED.toString() == nature || Nature.IMPISH.toString() == nature || Nature.LAX.toString() == nature)) {
            return 1.1f
        }
        if(statName == "spatk" && (Nature.MODEST.toString() == nature || Nature.MILD.toString() == nature || Nature.QUIET.toString() == nature || Nature.RASH.toString() == nature)) {
            return 1.1f
        }
        if(statName == "spdef" && (Nature.CALM.toString() == nature || Nature.GENTLE.toString() == nature || Nature.SASSY.toString() == nature || Nature.CAREFUL.toString() == nature)) {
            return 1.1f
        }
        if(statName == "spd" && (Nature.TIMID.toString() == nature || Nature.HASTY.toString() == nature || Nature.JOLLY.toString() == nature || Nature.NAIVE.toString() == nature)) {
            return 1.1f
        }

        if(statName == "atk" && (Nature.BOLD.toString() == nature || Nature.MODEST.toString() == nature || Nature.CALM.toString() == nature || Nature.TIMID.toString() == nature)) {
            return 0.9f
        }
        if(statName == "def" && (Nature.LONELY.toString() == nature || Nature.MILD.toString() == nature || Nature.GENTLE.toString() == nature || Nature.TIMID.toString() == nature)) {
            return 0.9f
        }
        if(statName == "spatk" && (Nature.ADAMANT.toString() == nature || Nature.IMPISH.toString() == nature || Nature.CAREFUL.toString() == nature || Nature.JOLLY.toString() == nature)) {
            return 0.9f
        }
        if(statName == "spdef" && (Nature.NAUGHTY.toString() == nature || Nature.LAX.toString() == nature || Nature.RASH.toString() == nature || Nature.NAIVE.toString() == nature)) {
            return 0.9f
        }
        if(statName == "spd" && (Nature.BRAVE.toString() == nature || Nature.RELAXED.toString() == nature || Nature.QUIET.toString() == nature || Nature.SASSY.toString() == nature)) {
            return 0.9f
        }
        return 1f
    }

    override fun onCleared() {
        super.onCleared()

    }
}


