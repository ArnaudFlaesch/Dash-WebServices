package com.dash.entity

import com.dash.enums.RoleEnum
import javax.persistence.*

@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private var name: RoleEnum? = null

    constructor() {}
    constructor(name: RoleEnum?) {
        this.name = name
    }

    fun getName(): RoleEnum? {
        return name
    }

    fun setName(name: RoleEnum?) {
        this.name = name
    }
}
