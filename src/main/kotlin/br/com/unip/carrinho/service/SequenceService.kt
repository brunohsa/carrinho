package br.com.unip.carrinho.service

import br.com.unip.carrinho.repository.entity.SequenceId
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class SequenceService(val mongoOperation: MongoOperations) : ISequenceService {

    override fun getNextSequenceId(key: String): Long? {
        val query = Query(Criteria.where("_id").`is`(key))

        val update = Update()
        update.inc("seq", 1)

        val options = FindAndModifyOptions()
        options.returnNew(true)

        val seqId = mongoOperation.findAndModify(query, update, options, SequenceId::class.java)
        return seqId?.seq
    }
}