package br.com.unip.carrinho.repository

import br.com.unip.carrinho.repository.entity.DatabaseSequence
import org.springframework.data.mongodb.repository.MongoRepository

interface ISequenceRepository : MongoRepository<DatabaseSequence, String>