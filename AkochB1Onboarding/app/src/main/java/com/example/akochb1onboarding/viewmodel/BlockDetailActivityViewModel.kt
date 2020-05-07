package com.example.akochb1onboarding.viewmodel

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.akochb1onboarding.domain.entity.Block
import org.json.JSONObject

class BlockDetailActivityViewModel : ViewModel() {

    private var block: Block = Block()
    private val _blockLiveData: MutableLiveData<Block> = MutableLiveData<Block>()
    var blockLiveData: LiveData<Block> = _blockLiveData
        private set

    var rawBlockPretty: String = BLANK
    var rawBlockVisibility = GONE

    var rawBlockChecked = false
        set(value) {
            rawBlockVisibility = if (value) VISIBLE else GONE
            field = value
        }

    fun update(block: Block) {
        this.block = block
        _blockLiveData.value = block
        block.fullBlock?.let {
            val json = JSONObject(it)
            rawBlockPretty = json.toString(DEFAULT_INDENTATION)
        }
    }

    companion object {
        const val BLANK = ""
        const val DEFAULT_INDENTATION = 2
    }
}