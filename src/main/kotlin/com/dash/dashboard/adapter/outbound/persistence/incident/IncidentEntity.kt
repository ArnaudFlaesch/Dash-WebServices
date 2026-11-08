package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetEntity
import com.dash.dashboard.domain.model.incident.Incident
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "incident")
class IncidentEntity(
    @Id
    @SequenceGenerator(
        name = "incident-seq-gen",
        sequenceName = "incident_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    @Column(name = "last_incident_date") var lastIncidentDate: OffsetDateTime,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "widgetId")
    val widget: WidgetEntity
)
