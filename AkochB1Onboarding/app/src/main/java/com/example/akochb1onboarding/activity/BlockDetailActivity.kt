package com.example.akochb1onboarding.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import com.example.akochb1onboarding.databinding.ActivityBlockDetailBinding
import com.example.akochb1onboarding.domain.entity.Block
import com.example.akochb1onboarding.viewmodel.BlockDetailActivityViewModel

class BlockDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: BlockDetailActivityViewModel
    private lateinit var binding: ActivityBlockDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(BlockDetailActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val block: Block? = intent.extras?.getParcelable(BLOCK_EXTRA_KEY)
        block?.run {
            bindData(this)
            viewModel.update(this)
        } ?: finish()
    }

    private fun bindData(block: Block) {
        with(binding) {
            producerTextView.text = block.producer
            producerSignatureTextView.text = block.producerSignature
            transactionNumberTextView.text = block.transactionNumber.toString()
            rawBlockTextView.text = block.getPrettyRawBlock()
            showRawBlockCheckBox.setOnCheckedChangeListener { _, selected ->
                rawBlockTextView.visibility = if (selected) VISIBLE else GONE
            }
        }
    }

    companion object {
        const val BLOCK_EXTRA_KEY = "PARCELABLE_BLOCK"
        fun getIntent(source: AppCompatActivity, block: Block? = null): Intent {
            val intent = Intent(source, BlockDetailActivity::class.java)
            intent.putExtra(BLOCK_EXTRA_KEY, block)
            return intent
        }
    }
}
