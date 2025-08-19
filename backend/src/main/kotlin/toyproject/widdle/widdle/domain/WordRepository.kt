package toyproject.widdle.widdle.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WordRepository : JpaRepository<Word, String> {

    @Query(value = "select w from Word w where w.isUsed = false")
    fun findAllByNotUsedWord(): List<Word>

    fun existsByWordJamo(wordJamo: List<String>): Boolean
}