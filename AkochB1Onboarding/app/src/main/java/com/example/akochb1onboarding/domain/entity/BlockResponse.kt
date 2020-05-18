package com.example.akochb1onboarding.domain.entity

sealed class BlockResponse(val block: Block? = null, val error: String? = null)
class SuccessBlockResponse(block: Block): BlockResponse(block, null)
class ErrorBlockResponse(error: String): BlockResponse(null, error)