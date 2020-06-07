package com.dash.entity

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.json.JSONObject
import javax.persistence.*
import kotlin.jvm.Transient

@TypeDefs(
        TypeDef(name = "jsonb", typeClass = JSONObject::class)
)
@Entity
@Table(name = "widget")
data class Widget(
        @Id
        val id: Number,

        val type: Number,

        @Type(type = "jsonb")
        @Transient
        val data: JSONObject?

        //@ManyToOne(optional=false)
        //@JoinColumn(name="tabId", referencedColumnName="id")
        //val tab: Tab
)