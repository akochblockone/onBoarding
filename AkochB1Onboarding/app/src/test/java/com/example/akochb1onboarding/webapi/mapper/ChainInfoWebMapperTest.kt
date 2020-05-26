package com.example.akochb1onboarding.webapi.mapper

import com.example.akochb1onboarding.webapi.entity.ChainInfoWebEntity
import org.junit.Test

class ChainInfoWebMapperTest {

    private val mapper = ChainInfoWebMapper()

    @Test
    fun transform() {
        val chain = mapper.transform(getWebEntity())
        assert(chain != null)
        chain ?: return
        assert(chain.serverVersion == SERVER_VERSION)
        assert(chain.chainId == CHAIN_ID)
        assert(chain.headBlockNum == HEAD_BLOCK_NUM)
        assert(chain.lastIrreversibleBlockNum == LAST_IRREVERSIBLE_BLOCK_NUM)
        assert(chain.lastIrreversibleBlockId == LAST_IRREVERSIBLE_BLOCK_ID)
        assert(chain.headBlockId == HEAD_BLOCK_ID)
        assert(chain.headBlockTime == HEAD_BLOCK_TIME)
        assert(chain.headBlockProducer == HEAD_BLOCK_PRODUCER)
        assert(chain.virtualBlockCpuLimit == VIRTUAL_BLOCK_CPU_LIMIT)
        assert(chain.virtualBlockNetLimit == VIRTUAL_BLOCK_NET_LIMIT)
        assert(chain.blockCpuLimit == BLOCK_CPU_LIMIT)
        assert(chain.blockNetLimit == BLOCK_NET_LIMIT)
        assert(chain.serverVersionString == SERVER_VERSION_STRING)
        assert(chain.forkDbHeadBlockNum == FORK_DB_HEAD_BLOCK_NUM)
        assert(chain.forkDbHeadBlockId == FORK_DB_HEAD_BLOCK_ID)
        assert(chain.serveFullVersionString == SERVE_FULL_VERSION_STRING)
    }

    private fun getWebEntity(): ChainInfoWebEntity {
        return ChainInfoWebEntity().apply {
            serverVersion = SERVER_VERSION
            chainId = CHAIN_ID
            headBlockNum = HEAD_BLOCK_NUM
            lastIrreversibleBlockNum = LAST_IRREVERSIBLE_BLOCK_NUM
            lastIrreversibleBlockId = LAST_IRREVERSIBLE_BLOCK_ID
            headBlockId = HEAD_BLOCK_ID
            headBlockTime = HEAD_BLOCK_TIME
            headBlockProducer = HEAD_BLOCK_PRODUCER
            virtualBlockCpuLimit = VIRTUAL_BLOCK_CPU_LIMIT
            virtualBlockNetLimit = VIRTUAL_BLOCK_NET_LIMIT
            blockCpuLimit = BLOCK_CPU_LIMIT
            blockNetLimit = BLOCK_NET_LIMIT
            serverVersionString = SERVER_VERSION_STRING
            forkDbHeadBlockNum = FORK_DB_HEAD_BLOCK_NUM
            forkDbHeadBlockId = FORK_DB_HEAD_BLOCK_ID
            serveFullVersionString = SERVE_FULL_VERSION_STRING
        }
    }

    companion object {
        private const val SERVER_VERSION = "SERVER_VERSION"
        private const val CHAIN_ID = "CHAIN_ID"
        private const val HEAD_BLOCK_NUM = 1234L
        private const val LAST_IRREVERSIBLE_BLOCK_NUM = 1234L
        private const val LAST_IRREVERSIBLE_BLOCK_ID = "LAST_IRREVERSIBLE_BLOCK_ID"
        private const val HEAD_BLOCK_ID = "HEAD_BLOCK_ID"
        private const val HEAD_BLOCK_TIME = "2020-04-24T13:20:45.000"
        private const val HEAD_BLOCK_PRODUCER = "HEAD_BLOCK_PRODUCER"
        private const val VIRTUAL_BLOCK_CPU_LIMIT = 5L
        private const val VIRTUAL_BLOCK_NET_LIMIT = 6L
        private const val BLOCK_CPU_LIMIT = 7L
        private const val BLOCK_NET_LIMIT = 8L
        private const val SERVER_VERSION_STRING = "SERVER_VERSION_STRING"
        private const val FORK_DB_HEAD_BLOCK_NUM = 9L
        private const val FORK_DB_HEAD_BLOCK_ID = "10"
        private const val SERVE_FULL_VERSION_STRING = "SERVE_FULL_VERSION_STRING"
    }
}