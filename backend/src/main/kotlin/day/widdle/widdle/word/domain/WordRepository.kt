package day.widdle.widdle.word.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WordRepository : JpaRepository<Word, String> {

    @Query(value = "select w from Word w where w.isUsed = false and w.wordInfo.isKorean = :isKorean")
    fun findAllByNotUsedWord(isKorean: Boolean): List<Word>

    @Query(value = "select w from Word w where w.usedDateBy = :date and w.wordInfo.isKorean = :isKorean")
    fun findByUsedDateByAndKoreanIs(date: LocalDate, isKorean: Boolean): Word?

    @Query(
        """
    select w
    from Word w
    where w.usedDateByTs >= :start
      and w.usedDateByTs < :end
      and w.wordInfo.isKorean = :isKorean
    """
    )
    fun findByUsedDateByTsBetweenAndKoreanIs(start: Long, end: Long, isKorean: Boolean): Word?

    fun existsByWordInfoWordText(wordText: String): Boolean

}
