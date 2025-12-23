package day.widdle.widdle.ranking.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class DeviceId(
    @Column(name = "device_id", length = 100, unique = true, nullable = false)
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "deviceId는 비어있을 수 없습니다." }
        require(value.length in 2..100) { "deviceId는 2자 이상 100자 미만이여야 합니다." }
    }
}
