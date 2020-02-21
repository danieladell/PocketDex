package org.ieselcaminas.daniel.proyectoclase.teaminfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.daniel.proyectoclase.data.TeamMember
import org.ieselcaminas.daniel.proyectoclase.databinding.TeamInfoLayoutBinding

class TeamInfoAdapter(private val onClickListener: OnClickListener) : ListAdapter<TeamMember, TeamInfoAdapter.TeamInfoViewHolder>(DiffCallback) {

    class TeamInfoViewHolder(private var binding: TeamInfoLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(teamMember: TeamMember) {
            binding.item = teamMember
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<TeamMember>() {
        override fun areItemsTheSame(oldItem: TeamMember, newItem: TeamMember): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: TeamMember, newItem: TeamMember): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamInfoViewHolder {
        val bindView = TeamInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TeamInfoViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: TeamInfoViewHolder, position: Int) {
        val teamMember = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(teamMember)
        }
        holder.bind(teamMember)
    }

    class OnClickListener(val clickListener: (teamMember: TeamMember) -> Unit) {
        fun onClick(teamMember: TeamMember) = clickListener(teamMember)
    }

}