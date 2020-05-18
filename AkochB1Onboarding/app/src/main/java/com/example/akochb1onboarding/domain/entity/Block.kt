package com.example.akochb1onboarding.domain.entity

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.example.akochb1onboarding.webapi.BlockGson
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Block(
    val id: String? = null,
    val blockNum: Int? = null,
    val producer: String? = null,
    val producerSignature: String? = null,
    val previousBlock: String? = null,
    val transactionNumber: Int? = null,
    val fullBlock: String? = null,
    val creationTime: String? = null,
    var cached: Boolean = false
) : Parcelable {
    fun getPrettyRawBlock(): String = JSONObject(fullBlock ?: "").toString(2)
    fun getAsBlockGson(): BlockGson {
        val builder = GsonBuilder().create()
        return builder.fromJson(fullBlock, BlockGson::class.java)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Block>() {
            override fun areItemsTheSame(old: Block, new: Block): Boolean {
                return old.id === new.id
            }

            override fun areContentsTheSame(old: Block, new: Block): Boolean {
                return old.id?.equals(new.id) ?: false
            }
        }
    }
}