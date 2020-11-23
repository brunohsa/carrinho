package br.com.unip.carrinho.service

import br.com.unip.carrinho.repository.ISequenceRepository
import br.com.unip.carrinho.repository.entity.DatabaseSequence
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.time.LocalDate

@Service
class SequenceService(val mongoOperation: MongoOperations,
                      val sequenceRepository: ISequenceRepository) : ISequenceService {

    private val FORMATO_NUMERO_PEDIDO = "####0000"

    override fun getSequenceNumeroPedido(key: String): String {
        val sequence = sequenceRepository.findById(key).orElse(DatabaseSequence(key))
        sequence.seq += 1
        sequenceRepository.save(sequence)
        return DecimalFormat(FORMATO_NUMERO_PEDIDO).format(sequence.seq)
    }

    override fun getSequence(key: String): Long {
        val criteria = Criteria.where("_id").`is`(key)
        val query = Query(criteria)

        val update = Update()
        update.inc("seq", 1)

        val options = FindAndModifyOptions()
        options.returnNew(true)
        options.upsert(true)

        val dbSeq = mongoOperation.findAndModify(query, update, options, DatabaseSequence::class.java)
        return dbSeq?.seq ?: 1
    }
}