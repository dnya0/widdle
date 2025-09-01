package toyproject.widdle.widdle.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WordRepository : JpaRepository<Word, String> {

    @Query(value = "select w from Word w where w.isUsed = false and w.isKorean = :isKorean")
    fun findAllByNotUsedWord(isKorean: Boolean): List<Word>

    @Query(value = "select w from Word w where w.usedDateBy = :date and w.isKorean = :isKorean")
    fun findByUsedDateByAndKoreanIs(date: LocalDate, isKorean: Boolean): Word?

    fun existsByWordText(wordText: String): Boolean

    @Query(
        value = """
            select exists(
              select 1
              from word
              where word_jamo = (:wordJamo)::text[]
            )
        """,
        nativeQuery = true
    )
    fun existsByWordJamo(@Param("wordJamo") wordJamo: Array<String>): Boolean
}