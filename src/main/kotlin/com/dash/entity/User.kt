package com.dash.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"]), UniqueConstraint(columnNames = ["email"])])
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotBlank
    @Size(max = 20)
    var username: String? = null

    @NotBlank
    @Size(max = 50)
    @Email
    var email: String? = null

    @NotBlank
    @Size(max = 120)
    var password: String? = null

    @ManyToOne
    @JoinColumn(name = "roleId")
    var role: Role? = null

    constructor() {}
    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }
}
