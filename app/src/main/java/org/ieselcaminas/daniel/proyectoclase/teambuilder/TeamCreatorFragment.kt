package org.ieselcaminas.daniel.proyectoclase.teambuilder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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

        binding.seekBarhp.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.hpEvs.setText("$i")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBaratk.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.atkEvs.setText("$i")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBardef.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.defEvs.setText("$i")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBarspatk.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.spatkEvs.setText("$i")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBarspdef.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.spdefEvs.setText("$i")

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.seekBarspd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    binding.spdEvs.setText("$i")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.addButton.setOnClickListener {
            val moves = listOf(binding.editMove1.text.toString(),binding.editMove2.text.toString(),
                                            binding.editMove3.text.toString(),binding.editMove4.text.toString())

            val evs = listOf(binding.hpEvs.text.toString(),binding.atkEvs.text.toString(),
                                            binding.defEvs.text.toString(),binding.spatkEvs.text.toString(),
                                            binding.spdefEvs.text.toString(),binding.spdEvs.text.toString())

            val member = TeamMember("", "", binding.editName.text.toString(), binding.editAbility.text.toString(), evs, binding.editItem.text.toString(), moves)
            fireData.updateMember(teamMember.idD, member)
            this.findNavController().navigateUp()
        }

        return binding.root
    }




}
