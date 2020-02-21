package org.ieselcaminas.daniel.proyectoclase.team


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember
import org.ieselcaminas.daniel.proyectoclase.databinding.FragmentTeamBinding

class TeamFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var adapter: TeamMiniAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        binding.lifecycleOwner = this

        binding.item = viewModel

        adapter = TeamMiniAdapter(TeamMiniAdapter.OnClickListener {
            viewModel.displayTeamDetails(it)
        })

        binding.recycler.adapter = adapter

        viewModel.navigateToSelectedTeam.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(TeamFragmentDirections.actionProfileIdToTeamInfoFragment(it))

                viewModel.displayTeamDetailsComplete()
            }
        })

        binding.addButton.setOnClickListener {
            val emptyList = ArrayList<TeamMember>()
            for(i in 0..5) {
                emptyList.add(TeamMember("", "", "", "", "", "", listOf("","","","")))
            }
            this.findNavController().navigate(TeamFragmentDirections.actionProfileIdToTeamInfoFragment(
                Team("", "", emptyList))
            )
        }

        return binding.root
    }


}
