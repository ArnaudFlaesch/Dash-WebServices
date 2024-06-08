package com.dash.infra.entity.incidentwidget

import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "incident_streak")
data class IncidentStreakEntity(
    @Id
    @SequenceGenerator(
        name = "incident-streak-seq-gen",
        sequenceName = "incident_streak_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident-streak-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    @Column(name = "streak_start_date") val streakStartDate: OffsetDateTime,
    @Column(name = "streak_end_date") val streakEndDate: OffsetDateTime,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "incidentId")
    val incident: IncidentEntity
) {
    fun toDomain(): IncidentStreakDomain =
        IncidentStreakDomain(
            id = id,
            streakStartDate = streakStartDate,
            streakEndDate = streakEndDate,
            incidentId = incident.id
        )
}
