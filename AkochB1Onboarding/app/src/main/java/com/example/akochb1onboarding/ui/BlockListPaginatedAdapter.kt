package com.example.akochb1onboarding.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.akochb1onboarding.R
import com.example.akochb1onboarding.db.entity.BlockEntity
import com.example.akochb1onboarding.domain.entity.Block

class BlockListPaginatedAdapter :
    PagedListAdapter<BlockEntity, BlockListPaginatedAdapter.BlockListViewHolder>(BlockEntity.DIFF_CALLBACK) {

    var onClickCallback: ((block: Block) -> Unit)? = null

    fun clearListener() {
        onClickCallback = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_block_layout, parent, false)
        return BlockListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentList?.size ?: 0
    }

    override fun onBindViewHolder(holder: BlockListViewHolder, position: Int) {
        val block = currentList?.get(position)
        holder.bind(block, position)
        holder.itemView.setOnClickListener {
            block?.let {
                onClickCallback?.invoke(it.toBlock())
            }
        }
    }

    class BlockListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val producer = itemView.findViewById<TextView>(R.id.producerTextView)
        val blockNum = itemView.findViewById<TextView>(R.id.blockNumberTextView)
        val cached = itemView.findViewById<View>(R.id.cachedIndicatorView)
        val nonCached = itemView.findViewById<View>(R.id.nonCachedIndicatorView)

        fun bind(block: BlockEntity?, position: Int) {
            producer.text = block?.producer ?: ""
            blockNum.text = "($position) ${block?.blockNum ?: "N/A"}"
//            if (block.cached) {
//                cached.visibility = VISIBLE
//                nonCached.visibility = GONE
//            } else {
//                cached.visibility = GONE
//                nonCached.visibility = VISIBLE
//            }
        }
    }
}