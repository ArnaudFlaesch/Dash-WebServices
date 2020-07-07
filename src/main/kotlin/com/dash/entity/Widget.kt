package com.dash.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.io.Serializable
import javax.persistence.*

@Entity
@TypeDef(name = "json", typeClass = JsonBinaryType::class)
data class Widget(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        var type: Int,

        @Type(type = "json")
        @Column(columnDefinition = "json")
        var data: Any?,

        var widgetOrder: Int?,

        @ManyToOne(optional = false)
        @JoinColumn(name = "tabId")
        var tab: Tab?
) : Serializable
