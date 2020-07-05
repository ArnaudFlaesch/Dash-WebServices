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
        @GeneratedValue
        val id: Int,

        var type: Int,

        @Type(type = "json")
        @Column(columnDefinition = "json")
        var data: Any?,

        var widgetOrder: Int?,

        @ManyToOne(optional = true)
        @JoinColumn(name = "tabId", referencedColumnName = "id")
        var tab: Tab?
) : Serializable
