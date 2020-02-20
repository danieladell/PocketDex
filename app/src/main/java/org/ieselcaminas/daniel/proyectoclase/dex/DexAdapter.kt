package org.ieselcaminas.daniel.proyectoclase.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.databinding.DexLayoutBinding


class DexAdapter(private val onClickListener: OnClickListener) : ListAdapter<Species, DexAdapter.DexAdapterViewHolder>(DiffCallback) {

    class DexAdapterViewHolder(private var binding: DexLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(species: Species) {
            binding.item = species
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Species>() {
        override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Species, newItem: Species): Boolean {
            return oldItem.id_species == newItem.id_species
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DexAdapterViewHolder {
        val bindView = DexLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DexAdapterViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: DexAdapterViewHolder, position: Int) {
        val dexListInfo = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(dexListInfo)
        }
        holder.bind(dexListInfo)
    }

    class OnClickListener(val clickListener: (species: Species) -> Unit) {
        fun onClick(species: Species) = clickListener(species)
    }

}
