package com.example.akochb1onboarding.activity

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.akochb1onboarding.R
import com.example.akochb1onboarding.domain.entity.Block

class BlockDetailFragment : Fragment(R.layout.fragment_block_detail) {

    private val args: BlockDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData(view, args.blockInfo)
    }

    private fun bindData(view: View, block: Block) {

        view.findViewById<TextView>(R.id.producerTextView)?.text = block.producer
        view.findViewById<TextView>(R.id.producerSignatureTextView)?.text = block.producerSignature
        view.findViewById<TextView>(R.id.transactionNumberTextView)?.text =
            block.transactionNumber.toString()
        val raw = view.findViewById<TextView>(R.id.rawBlockTextView)
        raw?.text = block.getPrettyRawBlock()
        val checkbox = view.findViewById<CheckBox>(R.id.showRawBlockCheckBox)
        checkbox?.setOnCheckedChangeListener { _, selected ->
            raw.visibility = if (selected) View.VISIBLE else View.GONE
        }
    }
}
