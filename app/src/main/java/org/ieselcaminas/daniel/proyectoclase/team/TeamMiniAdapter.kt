package org.ieselcaminas.daniel.proyectoclase.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.daniel.proyectoclase.data.Team
import org.ieselcaminas.daniel.proyectoclase.databinding.TeamMiniLayoutBinding

class TeamMiniAdapter(private val onClickListener: OnClickListener) : ListAdapter<Team, TeamMiniAdapter.TeamMiniViewHolder>(DiffCallback) {

    class TeamMiniViewHolder(private var binding: TeamMiniLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.item = team
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMiniViewHolder {
        val bindView = TeamMiniLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TeamMiniViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: TeamMiniViewHolder, position: Int) {
        val team = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(team)
        }
        holder.bind(team)
    }

    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }

}