package org.ieselcaminas.daniel.proyectoclase.teaminfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.daniel.proyectoclase.R

import org.ieselcaminas.daniel.proyectoclase.databinding.TeamInfoFragmentBinding
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamInfoFragment : Fragment() {

    private lateinit var viewModel: TeamInfoViewModel
    private lateinit var adapter: TeamInfoAdapter
    private lateinit var viewModelFactory: TeamInfoViewModelFactory
    private val fireData = FirestoreData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TeamInfoFragmentBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        val teamMembersId = TeamInfoFragmentArgs.fromBundle(arguments!!).idTeam

        viewModelFactory = TeamInfoViewModelFactory(teamMembersId,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TeamInfoViewModel::class.java)

        binding.lifecycleOwner = this
        binding.item = viewModel

        adapter = TeamInfoAdapter(TeamInfoAdapter.OnClickListener {
            viewModel.displayTeamMemberDetails(it)
        })
        binding.recycler.adapter = adapter

        viewModel.navigateToSelectedTeamMember.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(TeamInfoFragmentDirections.actionTeamInfoFragmentToTeamId(it))
                viewModel.displayTeamMemberDetailsComplete()
            }
        })
        binding.deleteButton.setOnClickListener {
           delete(teamMembersId)
        }

        return binding.root
    }

    private fun delete(id: String) {
        fireData.delete(id)
        this.findNavController().navigateUp()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamInfoViewModel::class.java)
    }

}
