package org.ieselcaminas.daniel.proyectoclase.firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.data.Stats
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember


class FirestoreData {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid ?: "wrongUser" }

    fun createUser() {
        val user = hashMapOf(
            "name" to userId
        )
        firebaseDB.collection("users").document(userId).set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getTeamData(): MutableLiveData<MutableList<Team>> {
        val mutableLiveData = MutableLiveData<MutableList<Team>>()
        val listData = mutableListOf<Team>()

        firebaseDB.collection("user").document(userId).collection("teams").addSnapshotListener { snapshots, ex ->
            for(dc in snapshots!!.documentChanges) {
                val document = dc.document

                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val id_team = document.getString("id_team")!!
                        val name = document.getString("team_name")!!

                        listData.add(Team(id_team, name, ArrayList()))
                                //getTeamMembersData(id_team)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val id_stat = document.getString("id_team")!!
                        val name = document.getString("team_name")!!

                        listData[listData.indexOf(listData.find { document.id == it.id })] =
                            Team(id_stat, name, getTeamMembersData(id_stat))
                    }
                    DocumentChange.Type.REMOVED -> {
                        listData.removeAt(listData.indexOf(listData.find { document.id == it.id}))
                    }
                }
            }
            //Log.i("TEST", listData[0].name)
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }

    private fun getTeamMembersData(id: String): List<TeamMember> {
        var listTeamMember = ArrayList<TeamMember>()
        val listData = ArrayList<TeamMember>()

        firebaseDB.collection("users").document(userId).collection("teams").document(id).collection("teammembers").addSnapshotListener { snapshots, ex ->
            for(dc in snapshots!!.documentChanges) {
                val document = dc.document

                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val idD = document.id
                        val id_team = document.getString("id_team")!!
                        val name = document.getString("name")!!
                        val ability = document.getString("ability")!!
                        val evs = document.getString("evs")!!
                        val item = document.getString("item")!!
                        val moves = document.getString("moves")!!

                        val movesSplitted = moves.split(";")

                        listData.add(TeamMember(idD, id_team, name, ability, evs, item, movesSplitted))
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val idD = document.id
                        val id_team = document.getString("id_team")!!
                        val name = document.getString("name")!!
                        val ability = document.getString("ability")!!
                        val evs = document.getString("evs")!!
                        val item = document.getString("item")!!
                        val moves = document.getString("moves")!!

                        val movesSplitted = moves.split(";")

                        listData[listData.indexOf(listData.find { document.id == it.idD })] =
                            TeamMember(idD, id_team, name, ability, evs, item, movesSplitted)
                    }
                    DocumentChange.Type.REMOVED -> {
                        listData.removeAt(listData.indexOf(listData.find { document.id == it.idD}))
                    }
                }
            }
            listTeamMember = listData
        }
        return listTeamMember
    }

    fun getStats(): MutableLiveData<MutableList<Stats>> {
        val mutableLiveData = MutableLiveData<MutableList<Stats>>()
        val listData = mutableListOf<Stats>()

        firebaseDB.collection("stats").orderBy("id").addSnapshotListener { snapshots, ex ->
            for(dc in snapshots!!.documentChanges) {
                val document = dc.document

                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val idD = document.id
                        val id_stat = document.getDouble("id")!!.toInt()
                        val species = document.getString("species")!!
                        val hp = document.getDouble("hp")!!.toInt()
                        val atk = document.getDouble("atk")!!.toInt()
                        val def = document.getDouble("def")!!.toInt()
                        val spatk = document.getDouble("spatk")!!.toInt()
                        val spdef = document.getDouble("spdef")!!.toInt()
                        val speed = document.getDouble("speed")!!.toInt()

                        //Log.i("test", type1)

                        listData.add(Stats(idD, id_stat, species, hp, atk, def, spatk, spdef, speed))
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val idD = document.id
                        val id_stat = document.getDouble("id")!!.toInt()
                        val species = document.getString("species")!!
                        val hp = document.getDouble("hp")!!.toInt()
                        val atk = document.getDouble("atk")!!.toInt()
                        val def = document.getDouble("def")!!.toInt()
                        val spatk = document.getDouble("spatk")!!.toInt()
                        val spdef = document.getDouble("spdef")!!.toInt()
                        val speed = document.getDouble("speed")!!.toInt()

                        listData[listData.indexOf(listData.find { document.id == it.idD })] =
                            Stats(idD, id_stat, species, hp, atk, def, spatk, spdef, speed)
                    }
                    DocumentChange.Type.REMOVED -> {
                        listData.removeAt(listData.indexOf(listData.find { document.id == it.idD}))
                    }
                }
            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }

    fun getSpecies(): MutableLiveData<MutableList<Species>> {
        val mutableLiveData = MutableLiveData<MutableList<Species>>()
        val listData = mutableListOf<Species>()

        firebaseDB.collection("species").orderBy("id").addSnapshotListener { snapshots, ex ->
            for(dc in snapshots!!.documentChanges) {
                val document = dc.document

                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val idD = document.id
                        val image = document.getString("image")
                        val name = document.getString("name")
                        val id = document.getLong("id")
                        val type1 = document.getString("type1")!!
                        val type2 = document.getString("type2")!!
                        val height = document.getDouble("height")!!.toFloat()
                        val weight = document.getDouble("weight")!!.toFloat()
                        val descrip = document.getString("descriptions")!!

                        //Log.i("test", type1)

                        listData.add(
                            Species(
                                idD,
                                id!!,
                                image!!,
                                name!!,
                                type1,
                                type2,
                                height,
                                weight,
                                descrip
                            )
                        )
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val idD = document.id
                        val image = document.getString("image")
                        val name = document.getString("name")
                        val id = document.getLong("id")
                        val type1 = document.getString("type1")!!
                        val type2 = document.getString("type2")!!
                        val height = document.getDouble("height")!!.toFloat()
                        val weight = document.getDouble("weight")!!.toFloat()
                        val descrip = document.getString("descriptions")!!

                        listData[listData.indexOf(listData.find { document.id == it.idD })] =
                            Species(
                                idD,
                                id!!,
                                image!!,
                                name!!,
                                type1,
                                type2,
                                height,
                                weight,
                                descrip
                            )
                    }
                    DocumentChange.Type.REMOVED -> {
                        listData.removeAt(listData.indexOf(listData.find { document.id == it.idD }))
                    }
                }
            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }



}


