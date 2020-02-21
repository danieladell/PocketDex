package org.ieselcaminas.daniel.proyectoclase.dex

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import org.ieselcaminas.daniel.proyectoclase.R
import org.ieselcaminas.daniel.proyectoclase.databinding.DexFragmentBinding
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData



class DexFragment : Fragment() {

    private lateinit var adapter: DexAdapter
    private val dexViewModel by lazy {ViewModelProvider(this).get(DexViewModel::class.java)}
    private val firestoreData by lazy { FirestoreData() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DexFragmentBinding.inflate(inflater)


        binding.lifecycleOwner = this

        binding.dexViewModel = dexViewModel

       adapter = DexAdapter(DexAdapter.OnClickListener {
            dexViewModel.displaySpeciesDetails(it)
        })

        binding.recycler.adapter = adapter

        dexViewModel.navigateToSelectedSpecie.observe(viewLifecycleOwner, Observer {
            it?.let {
                val idD: String = it.idD
                val name: String = it.name
                val image: String = it.image
                val id_species: Long = it.id_species
                val description: String = it.description
                val weight: Float = it.weight
                val height: Float = it.height
                val type1: String = it.type1
                val type2: String = it.type2
                this.findNavController()
                .navigate(DexFragmentDirections.actionDexFragmentToDexInfo(id_species, name, image, weight, height, description, type2, type1, idD))

                dexViewModel.displaySpeciesDetailsComplete()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        Log.i("test", "on resume")
    }

}
