package day.widdle.widdle

import day.widdle.widdle.global.support.JamoCombiner
import day.widdle.widdle.global.support.JamoCombiner.preprocessJamo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.context.SpringBootTest

@Disabled("서버에서 조합하지 않도록 변경됨")
@SpringBootTest
class JamoTest {

    @Test
    fun 자모를_받으면_단어로_조합할_수_있는가() {
        assertAll(
            {
                val actual = JamoCombiner.assemble(preprocessJamo(listOf("ㄱ", "ㅏ", "ㄴ", "ㅏ", "ㄷ", "ㅣ")))
                assertThat(actual).isEqualTo("가나디")
                println(actual)
            },
            {
                val actual = JamoCombiner.assemble(preprocessJamo(listOf("ㅎ", "ㅏ", "ㄱ", "ㄱ", "ㅕ", "ㅣ")))
                assertThat(actual).isEqualTo("학계")
                println(actual)
            },
            {
                val actual = JamoCombiner.assemble(preprocessJamo(listOf("ㄱ", "ㄱ", "ㅏ", "ㅊ", "ㅣ")))
                assertThat(actual).isEqualTo("까치")
                println(actual)
            }
        )

    }

}
