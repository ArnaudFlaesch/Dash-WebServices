package com.dash.repository

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.dialect.H2Dialect
import java.sql.Types

internal class DashDatabaseDialect : H2Dialect() {
    init {
        registerHibernateType(Types.OTHER, JsonBinaryType::class.java.name)
    }
}
