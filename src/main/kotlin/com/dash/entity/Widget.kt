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
        val id: Int,

        val type: Int,

        @Type(type = "json")
        @Column(columnDefinition = "json")
        val data: Any?,

        val widgetOrder: Int?,

        @ManyToOne(optional = true)
        @JoinColumn(name = "tabId", referencedColumnName = "id")
        val tab: Tab?
) : Serializable
