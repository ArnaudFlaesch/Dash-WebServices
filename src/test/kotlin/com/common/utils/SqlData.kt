package com.common.utils

import org.springframework.test.context.jdbc.Sql

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Sql(
    scripts = ["classpath:./data/dash-data.sql", "classpath:./data/cashmanager-data.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
annotation class SqlData
