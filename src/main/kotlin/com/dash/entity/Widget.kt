package com.dash.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
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
@TypeDef(name = "json", typeClass = JsonBinaryType::class)
data class Widget(
        @Id		
		@SequenceGenerator(name="widget-seq-gen", sequenceName="WIDGET_SEQ", initialValue=1, allocationSize=1)
		@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="widget-seq-gen")
		@Column(name="id",unique=true,nullable=false)
        val id: Int = 0,

        var type: Int? = null,

        @Type(type = "json")
        @Column(columnDefinition = "json")
        var data: Any? = null,

        var widgetOrder: Int? = 0,

        @ManyToOne(optional = true)
        @JoinColumn(name = "tabId")
        var tab: Tab?
) : Serializable
