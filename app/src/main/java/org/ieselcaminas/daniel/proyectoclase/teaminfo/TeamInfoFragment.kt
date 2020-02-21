package org.ieselcaminas.daniel.proyectoclase.teaminfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import org.ieselcaminas.daniel.proyectoclase.databinding.TeamInfoFragmentBinding

class TeamInfoFragment : Fragment() {

    private lateinit var viewModel: TeamInfoViewModel
    private lateinit var adapter: TeamInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TeamInfoFragmentBinding.inflate(inflater)

        val teamMembers = TeamInfoFragmentArgs.fromBundle(arguments!!).team.members

        adapter = TeamInfoAdapter(TeamInfoAdapter.OnClickListener {

        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamInfoViewModel::class.java)
    }

}
