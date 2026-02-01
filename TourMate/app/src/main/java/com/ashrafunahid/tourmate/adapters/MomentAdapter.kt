package com.ashrafunahid.tourmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashrafunahid.tourmate.databinding.MomentRowBinding
import com.ashrafunahid.tourmate.models.MomentModel
import com.bumptech.glide.Glide

class MomentAdapter :
    ListAdapter<MomentModel, MomentAdapter.MomentViewHolder>(MomentDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MomentViewHolder {
        val binding = MomentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MomentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MomentViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.bind(model)
    }

    class MomentViewHolder(val binding: MomentRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(momentModel: MomentModel) {
            Glide.with(binding.root.context)
                .load(momentModel.imageUrl)
                .into(binding.momentRowImageView)
        }
    }

    class MomentDiffCallback : DiffUtil.ItemCallback<MomentModel>() {
        override fun areItemsTheSame(
            oldItem: MomentModel,
            newItem: MomentModel
        ): Boolean {
            return oldItem.momentId == newItem.momentId
        }

        override fun areContentsTheSame(
            oldItem: MomentModel,
            newItem: MomentModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}