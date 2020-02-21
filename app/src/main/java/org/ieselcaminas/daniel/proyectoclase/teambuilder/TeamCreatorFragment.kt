package org.ieselcaminas.daniel.proyectoclase.teambuilder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.ieselcaminas.daniel.proyectoclase.R

class TeamCreatorFragment : Fragment() {

    companion object {
        fun newInstance() = TeamCreatorFragment()
    }

    private lateinit var viewModel: TeamCreatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_creator_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamCreatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
