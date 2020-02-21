package org.ieselcaminas.daniel.proyectoclase.team


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember
import org.ieselcaminas.daniel.proyectoclase.databinding.FragmentTeamBinding
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData

class TeamFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var adapter: TeamMiniAdapter
    private val fireData = FirestoreData()

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
                this.findNavController().navigate(TeamFragmentDirections.actionProfileIdToTeamInfoFragment(it.id))

                viewModel.displayTeamDetailsComplete()
            }
        })

        binding.addButton.setOnClickListener {
            if(binding.teamNameEdit.text.toString() != "") {
                fireData.createTeam(binding.teamNameEdit.text.toString())
                binding.teamNameEdit.setText("")
            }else{
                Toast.makeText(context, "Write a team name pls!", Toast.LENGTH_SHORT).show()

            }
        }

        return binding.root
    }


}
