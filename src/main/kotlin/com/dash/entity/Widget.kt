package com.dash.entity

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.json.JSONObject
import java.io.Serializable
import javax.persistence.*

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class Widget(
        @Id
        val id: Int,

        val type: Int,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        val data: Any,

        val widgetOrder: Int?,

        @ManyToOne(optional = true)
        @JoinColumn(name = "tabId", referencedColumnName = "id")
        val tab: Tab?
) : Serializable