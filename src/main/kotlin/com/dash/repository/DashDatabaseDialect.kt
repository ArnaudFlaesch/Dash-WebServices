package com.dash.repository

import org.hibernate.dialect.PostgreSQL10Dialect
import java.sql.Types


internal class DashDatabaseDialect : PostgreSQL10Dialect() {
    init {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb")
    }
}