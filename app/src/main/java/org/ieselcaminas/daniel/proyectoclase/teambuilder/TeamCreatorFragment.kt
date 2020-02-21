package org.ieselcaminas.daniel.proyectoclase.teambuilder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.daniel.proyectoclase.R
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember

import org.ieselcaminas.daniel.proyectoclase.databinding.TeamCreatorFragmentBinding
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamCreatorFragment : Fragment() {

    private lateinit var viewModel: TeamCreatorViewModel
    private lateinit var viewModelFactory: TeamCreatorViewModelFactory
    private val fireData = FirestoreData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TeamCreatorFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        binding.lifecycleOwner = this

        val teamMember = TeamCreatorFragmentArgs.fromBundle(arguments!!).teamMember

        viewModelFactory = TeamCreatorViewModelFactory(teamMember, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TeamCreatorViewModel::class.java)

        binding.item = viewModel

        binding.addButton.setOnClickListener {
            val moves = listOf(binding.editMove1.text.toString(),binding.editMove2.text.toString(),binding.editMove3.text.toString(),binding.editMove4.text.toString())
            val member = TeamMember("", "", binding.editName.text.toString(), binding.editAbility.text.toString(), " ", binding.editItem.text.toString(), moves)
            fireData.updateMember(teamMember.idD, member)
            this.findNavController().navigate(TeamCreatorFragmentDirections.actionTeamIdToIdTeamInfo(teamMember.id_team))
        }

        return binding.root
    }



}
