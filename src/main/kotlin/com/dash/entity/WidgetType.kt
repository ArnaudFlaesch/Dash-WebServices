package com.dash.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@TypeDef(name = "json", typeClass = JsonBinaryType::class)
data class WidgetType(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    val id: Int = 0,

    @Column(name = "description")
    val description: String,

    @Type(type = "json")
    @Column(columnDefinition = "json")
    var config: Any? = null
)
