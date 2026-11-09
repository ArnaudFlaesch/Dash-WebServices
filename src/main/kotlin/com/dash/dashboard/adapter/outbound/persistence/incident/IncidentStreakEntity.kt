package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.domain.model.incident.IncidentStreak
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "incident_streak")
class IncidentStreakEntity(
    @Id
    @SequenceGenerator(
        name = "incident-streak-seq-gen",
        sequenceName = "incident_streak_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident-streak-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    var id: Int,
    @Column(name = "streak_start_date") var streakStartDate: OffsetDateTime,
    @Column(name = "streak_end_date") var streakEndDate: OffsetDateTime,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "incidentId")
    var incident: IncidentEntity
)
