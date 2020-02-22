package org.ieselcaminas.daniel.proyectoclase.calculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.calculator_fragment.*
import org.ieselcaminas.daniel.proyectoclase.databinding.CalculatorFragmentBinding

class CalculatorFragment : Fragment() {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CalculatorFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)


        viewModel.speciesName.observe(viewLifecycleOwner, Observer {
            adapter = ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line, it)
            binding.speciesAutocomplete.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })

        viewModel.stats.observe(viewLifecycleOwner, Observer {
            binding.speciesAutocomplete.onItemClickListener = AdapterView.OnItemClickListener{
                    parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                for(i in it) {
                    if (i.species == selectedItem) {
                        binding.basehpText.text = i.hp.toString()
                        binding.baseatkText.text = i.atk.toString()
                        binding.basedefText.text = i.dfs.toString()
                        binding.basespatkText.text = i.sp_atk.toString()
                        binding.basespdefText.text = i.sp_dfs.toString()
                        binding.basespeedText.text = i.spd.toString()

                        binding.invalidateAll()
                    }
                }
            }
        })

        binding.calculateButton.setOnClickListener {
            val ivs = viewModel.calculateIvs(binding.speciesAutocomplete.text.toString(),
                binding.editHP.text.toString().toInt(),
                binding.editAtk.text.toString().toInt(),
                binding.editDef.text.toString().toInt(),
                binding.editSpAtk.text.toString().toInt(),
                binding.editSpDef.text.toString().toInt(),
                binding.editSpeed.text.toString().toInt(),
                binding.editLevel.text.toString().toInt(),
                binding.natureEdit.text.toString())

                binding.hpivtext.text = ivs[0].toString()
                binding.atkivtext.text = ivs[1].toString()
                binding.defivtext.text = ivs[2].toString()
                binding.spatkivtext.text = ivs[3].toString()
                binding.spdefivtext.text = ivs[4].toString()
                binding.spdivtext.text = ivs[5].toString()


        }




        return binding.root
    }




}
