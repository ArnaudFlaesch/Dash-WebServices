package com.dash.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
@TypeDefs(
    TypeDef(name = "json", typeClass = JsonStringType::class),
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
data class Widget(
    @Id
    @SequenceGenerator(name = "widget-seq-gen", sequenceName = "widget_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "widget-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    var type: Int? = null,

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    var data: Any? = null,

    var widgetOrder: Int? = 0,

    @ManyToOne(optional = true)
    @JoinColumn(name = "tabId")
    var tab: Tab?
) : Serializable
