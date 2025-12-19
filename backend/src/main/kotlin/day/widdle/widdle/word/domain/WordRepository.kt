package day.widdle.widdle.word.domain

import day.widdle.widdle.word.domain.vo.WordId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WordRepository : JpaRepository<Word, WordId> {

    @Query(value = "select w from Word w where w.isUsed = false and w.wordInfo.isKorean = :isKorean")
    fun findAllByNotUsedWord(isKorean: Boolean): List<Word>

    @Query(value = "select w from Word w where w.usedDateBy = :date and w.wordInfo.isKorean = :isKorean")
    fun findByUsedDateByAndKoreanIs(date: LocalDate, isKorean: Boolean): Word?

    /**
     * 주어진 타임스탬프 범위와 언어로 사용된 단어를 조회합니다.
     * 비즈니스 규칙: 하루에 하나의 단어만 할당되므로 최대 하나의 결과만 반환됩니다.
     */
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
