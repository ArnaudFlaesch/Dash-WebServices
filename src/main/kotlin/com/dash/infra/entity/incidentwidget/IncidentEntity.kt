package com.dash.infra.entity.incidentwidget

import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.infra.entity.WidgetEntity
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "incident")
data class IncidentEntity(
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

    @Column(name = "incident_name") val incidentName: String,

    @Column(name = "last_incident_date") val lastIncidentDate: OffsetDateTime?,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "widgetId")
    val widget: WidgetEntity
) {
    fun toDomain(): IncidentDomain = IncidentDomain(
        id = id,
        incidentName = incidentName,
        lastIncidentDate = lastIncidentDate,
        widgetId = widget.id
    )
}
