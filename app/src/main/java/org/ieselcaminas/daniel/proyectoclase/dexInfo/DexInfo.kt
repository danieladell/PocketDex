package org.ieselcaminas.daniel.proyectoclase.dexInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

import org.ieselcaminas.daniel.proyectoclase.R
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.databinding.DexInfoFragmentBinding
import org.ieselcaminas.daniel.proyectoclase.dex.DexInfoViewModelFactory

class DexInfo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DexInfoFragmentBinding.inflate(inflater)

        val application = this.activity!!.application
        binding.lifecycleOwner = this

        val specie = Species(
            DexInfoArgs.fromBundle(arguments!!).idd,
            DexInfoArgs.fromBundle(arguments!!).id,
            DexInfoArgs.fromBundle(arguments!!).image,
            DexInfoArgs.fromBundle(arguments!!).name,
            DexInfoArgs.fromBundle(arguments!!).type1,
            DexInfoArgs.fromBundle(arguments!!).type2,
            DexInfoArgs.fromBundle(arguments!!).height,
            DexInfoArgs.fromBundle(arguments!!).weight,
            DexInfoArgs.fromBundle(arguments!!).description
        )
        val viewModelFactory = DexInfoViewModelFactory(specie, application)
        val infoViewModel = ViewModelProvider(this, viewModelFactory).get(DexInfoViewModel::class.java)

        binding.infoViewModel = infoViewModel

        setTypeImage(specie.type1, binding.type1Image)
        setTypeImage(specie.type2, binding.type2Image)

        return binding.root
    }

    private fun setTypeImage(type: String, imageView: ImageView) {
        when(type) {
            "Grass" -> {imageView.setImageResource(R.drawable.planta)}
            "Poison" -> {imageView.setImageResource(R.drawable.veneno)}
            "Steel" -> {imageView.setImageResource(R.drawable.acero)}
            "Fairy" -> {imageView.setImageResource(R.drawable.normal)}
            "Bug" -> {imageView.setImageResource(R.drawable.bicho)}
            "Fire" -> {imageView.setImageResource(R.drawable.fuego)}
            "Water" -> {imageView.setImageResource(R.drawable.water)}
            "Flying" -> {imageView.setImageResource(R.drawable.volador)}
            "Normal" -> {imageView.setImageResource(R.drawable.normal)}
            "Electric" -> {imageView.setImageResource(R.drawable.electrico)}
            "Ground" -> {imageView.setImageResource(R.drawable.tierra)}
            "Psychic" -> {imageView.setImageResource(R.drawable.psiquico)}
            "Rock" -> {imageView.setImageResource(R.drawable.roca)}
            "Ice" -> {imageView.setImageResource(R.drawable.hielo)}
            "Dragon" -> {imageView.setImageResource(R.drawable.dragon)}
            "Fighting" -> {imageView.setImageResource(R.drawable.lucha)}
            "Ghost" -> {imageView.setImageResource(R.drawable.fantasma)}
            "" -> {}
        }
    }


}
