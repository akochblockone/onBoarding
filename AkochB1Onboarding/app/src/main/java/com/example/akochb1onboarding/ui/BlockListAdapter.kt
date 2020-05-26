package com.example.akochb1onboarding.ui

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.akochb1onboarding.R
import com.example.akochb1onboarding.domain.entity.Block

class BlockListAdapter :
    ListAdapter<Block, BlockListAdapter.BlockListViewHolder>(Block.DIFF_CALLBACK) {

    var onClickCallback: ((block: Block) -> Unit)? = null

    fun clearListener() {
        onClickCallback = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_block_layout, parent, false)
        return BlockListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlockListViewHolder, position: Int) {
        val block = getItem(position)
        holder.bind(block)
        holder.itemView.setOnClickListener {
            onClickCallback?.invoke(block)
        }
    }

    class BlockListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val producer = itemView.findViewById<TextView>(R.id.producerTextView)
        private val blockNum = itemView.findViewById<TextView>(R.id.blockNumberTextView)
        private val cached = itemView.findViewById<View>(R.id.cachedIndicatorView)
        private val nonCached = itemView.findViewById<View>(R.id.nonCachedIndicatorView)

        fun bind(block: Block) {
            producer.text = block.producer ?: NOT_AVAILABLE
            blockNum.text = "${block.blockNum ?: NOT_AVAILABLE}"
            if (block.cached) {
                cached.visibility = VISIBLE
                nonCached.visibility = GONE
            } else {
                cached.visibility = GONE
                nonCached.visibility = VISIBLE
            }
        }
    }

    companion object {
        const val NOT_AVAILABLE = "N/A"
    }
}