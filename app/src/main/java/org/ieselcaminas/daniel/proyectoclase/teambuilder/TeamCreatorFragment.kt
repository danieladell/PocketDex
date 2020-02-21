package org.ieselcaminas.daniel.proyectoclase.teambuilder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import org.ieselcaminas.daniel.proyectoclase.R
import org.ieselcaminas.daniel.proyectoclase.databinding.TeamCreatorFragmentBinding

class TeamCreatorFragment : Fragment() {

    private lateinit var viewModel: TeamCreatorViewModel
    private lateinit var viewModelFactory: TeamCreatorViewModelFactory


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

        return binding.root
    }



}
