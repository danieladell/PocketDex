package org.ieselcaminas.daniel.proyectoclase.firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

    val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid ?: "wrongUser" }

    fun createUser() {
        val user = hashMapOf(
            "name" to userId
        )
        firebaseDB.collection("users").document(userId).set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun delete(id_team: String) {
        firebaseDB.collection("teams").document(id_team).delete().addOnSuccessListener {  }.addOnFailureListener {  }
        firebaseDB.collection("teammembers").whereEqualTo("id_team", id_team).get().addOnSuccessListener {
            for(documents in it) {
                firebaseDB.collection("teammembers").document(documents.id).delete().addOnSuccessListener {  }.addOnFailureListener {  }
            }
        }.addOnFailureListener {  }
    }

    fun updateMember(id: String, teamMember: TeamMember) {
        var moves = ""
        var evs = ""

        for ((counter, i) in teamMember.moves.withIndex()) {
            moves += if (counter == teamMember.moves.size - 1) {
                i
            } else {
                "$i;"
            }
        }
        for ((counter, i) in teamMember.evs.withIndex()) {
            evs += if (counter == teamMember.evs.size - 1) {
                i
            } else {
                "$i;"
            }
        }
        firebaseDB.collection("teammembers").document(id).update("name", teamMember.name)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
        firebaseDB.collection("teammembers").document(id).update("ability", teamMember.ability)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
        firebaseDB.collection("teammembers").document(id).update("item", teamMember.item)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
        firebaseDB.collection("teammembers").document(id).update("evs", evs)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
        firebaseDB.collection("teammembers").document(id).update("moves", moves)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    fun createTeam(team_name: String) {
        val data = hashMapOf(
            "team_name" to team_name,
            "user" to userId
        )
        firebaseDB.collection("teams").add(data).addOnSuccessListener {
            Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}")
            setIdTeam(it.id)
            createTeamMembers(it.id)

        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
    }

    private fun createTeamMembers(idTeam: String) {
        for (i in 0..5) {
            val data = hashMapOf(
                "id_team" to idTeam,
                "name" to "",
                "ability" to "",
                "evs" to "0;0;0;0;0;0",
                "item" to "",
                "moves" to "",
                "user" to userId
            )
            firebaseDB.collection("teammembers").add(data).addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}")
                setIdTeamMember(it.id)
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        }

    }

    private fun setIdTeamMember(id: String) {
        firebaseDB.collection("teammembers").document(id).update("id_member", id)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

    }

    private fun setIdTeam(id: String) {
        firebaseDB.collection("team").document(id).update("id_team", id)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }

    }

    fun getTeamData(): MutableLiveData<MutableList<Team>> {
        val mutableLiveData = MutableLiveData<MutableList<Team>>()
        val listData = mutableListOf<Team>()

        firebaseDB.collection("teams").whereEqualTo("user", userId)
            .addSnapshotListener { snapshots, _ ->
                for (dc in snapshots!!.documentChanges) {
                    val document = dc.document

                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val id_team = document.id
                            val name = document.getString("team_name")!!

                            listData.add(Team(id_team, name, ArrayList()))
                            //getTeamMembersData(id_team)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val id_stat = document.id
                            val name = document.getString("team_name")!!

                            listData[listData.indexOf(listData.find { document.id == it.id })] =
                                Team(id_stat, name, ArrayList())
                        }
                        DocumentChange.Type.REMOVED -> {
                            listData.removeAt(listData.indexOf(listData.find { document.id == it.id }))
                        }
                    }
                }
                mutableLiveData.value = listData
            }
        return mutableLiveData
    }

    fun getTeamMembersData(id: String): MutableLiveData<MutableList<TeamMember>> {
        val listTeamMember = MutableLiveData<MutableList<TeamMember>>()
        val listData = ArrayList<TeamMember>()

        firebaseDB.collection("teammembers").whereEqualTo("user", userId)
            .whereEqualTo("id_team", id).addSnapshotListener { snapshots, _ ->
                for (dc in snapshots!!.documentChanges) {
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
                            val evsSplitted = evs.split(";")

                            listData.add(
                                TeamMember(
                                    idD,
                                    id_team,
                                    name,
                                    ability,
                                    evsSplitted,
                                    item,
                                    movesSplitted
                                )
                            )
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
                            val evsSplitted = evs.split(";")

                            listData[listData.indexOf(listData.find { document.id == it.idD })] =
                                TeamMember(
                                    idD,
                                    id_team,
                                    name,
                                    ability,
                                    evsSplitted,
                                    item,
                                    movesSplitted
                                )
                        }
                        DocumentChange.Type.REMOVED -> {
                            listData.removeAt(listData.indexOf(listData.find { document.id == it.idD }))
                        }
                    }
                }
                listTeamMember.value = listData
            }
        return listTeamMember
    }

    fun getStats(): MutableLiveData<MutableList<Stats>> {
        val mutableLiveData = MutableLiveData<MutableList<Stats>>()
        val listData = mutableListOf<Stats>()

        firebaseDB.collection("stats").orderBy("id").addSnapshotListener { snapshots, _ ->
            for (dc in snapshots!!.documentChanges) {
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

                        listData.add(
                            Stats(
                                idD,
                                id_stat,
                                species,
                                hp,
                                atk,
                                def,
                                spatk,
                                spdef,
                                speed
                            )
                        )
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
                        listData.removeAt(listData.indexOf(listData.find { document.id == it.idD }))
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

        firebaseDB.collection("species").orderBy("id").addSnapshotListener { snapshots, _ ->
            for (dc in snapshots!!.documentChanges) {
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


//DONT WORK ??

/*private fun getTeamMembersData(id: String): List<TeamMember> {
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
                        Team(id_stat, name, ArrayList())
                }
                DocumentChange.Type.REMOVED -> {
                    listData.removeAt(listData.indexOf(listData.find { document.id == it.id}))
                }
            }
        }
        mutableLiveData.value = listData
    }
    return mutableLiveData
}*/

